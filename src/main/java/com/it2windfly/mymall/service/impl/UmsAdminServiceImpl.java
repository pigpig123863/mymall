package com.it2windfly.mymall.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.bo.AdminUserDetails;
import com.it2windfly.mymall.dao.UmsAdminPermissionRelationDao;
import com.it2windfly.mymall.dao.UmsAdminRoleRelationDao;
import com.it2windfly.mymall.dto.UmsAdminParam;
import com.it2windfly.mymall.dto.UpdateAdminPasswordParam;
import com.it2windfly.mymall.mbg.mapper.UmsAdminLoginLogMapper;
import com.it2windfly.mymall.mbg.mapper.UmsAdminMapper;
import com.it2windfly.mymall.mbg.mapper.UmsAdminPermissionRelationMapper;
import com.it2windfly.mymall.mbg.mapper.UmsAdminRoleRelationMapper;
import com.it2windfly.mymall.mbg.model.UmsAdmin;
import com.it2windfly.mymall.mbg.model.UmsAdminExample;
import com.it2windfly.mymall.mbg.model.UmsAdminLoginLog;
import com.it2windfly.mymall.mbg.model.UmsAdminPermissionRelation;
import com.it2windfly.mymall.mbg.model.UmsAdminPermissionRelationExample;
import com.it2windfly.mymall.mbg.model.UmsAdminRoleRelation;
import com.it2windfly.mymall.mbg.model.UmsAdminRoleRelationExample;
import com.it2windfly.mymall.mbg.model.UmsPermission;
import com.it2windfly.mymall.mbg.model.UmsRole;
import com.it2windfly.mymall.security.utils.JwtTokenUtil;
import com.it2windfly.mymall.service.UmsAdminService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;


@Service
public class UmsAdminServiceImpl implements UmsAdminService{
	private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
	@Autowired UmsAdminMapper umsAdminMapper;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired UmsAdminRoleRelationDao umsAdminRoleRelationDao;
	@Autowired UmsAdminLoginLogMapper umsAdminLoginLogMapper;
	@Autowired UmsAdminPermissionRelationMapper umsAdminPermissionRelationMapper;
	@Autowired UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;
	@Autowired UmsAdminPermissionRelationDao umsAdminPermissionRelationDao;

	@Override
	public UmsAdmin getAdminByUsername(String username) {
		UmsAdminExample uae = new UmsAdminExample();
		uae.createCriteria().andUsernameEqualTo(username);
		List<UmsAdmin> list = umsAdminMapper.selectByExample(uae);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<UmsPermission> getPermissionList(Long adminId) {
		return umsAdminRoleRelationDao.getPermissionList(adminId);
	}

	@Override
	public String login(String username, String password) {
		String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

	private void insertLoginLog(String username) {
		UmsAdmin admin = getAdminByUsername(username);
		UmsAdminLoginLog log = new UmsAdminLoginLog();
		log.setAdminId(admin.getId());
		log.setCreateTime(new Date());
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
		log.setIp(request.getRemoteAddr());
		umsAdminLoginLogMapper.insert(log);
	}

	private UserDetails loadUserByUsername(String username) {
		UmsAdmin admin = getAdminByUsername(username);
		if (admin!=null){
		List<UmsPermission> upList = getPermissionList(admin.getId());
		return new AdminUserDetails(admin,upList);
		}
		throw new  UsernameNotFoundException("用户名或密码错误");
	}


	@Override
	public UmsAdmin register(UmsAdminParam umsAdminParam) {
		UmsAdmin umsAdmin =new UmsAdmin();
		umsAdmin.setCreateTime(new Date());
		umsAdmin.setStatus(1);
		BeanUtils.copyProperties(umsAdminParam, umsAdmin);
		
		UmsAdminExample example = new UmsAdminExample();
		example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
		List<UmsAdmin> list = umsAdminMapper.selectByExample(example);
		if(list.size()>0){
			return null;
		}
		String encode = passwordEncoder.encode(umsAdmin.getPassword());
		umsAdmin.setPassword(encode);
		umsAdminMapper.insert(umsAdmin);
		return umsAdmin;
	}

	@Override
	public String refreshToken(String oldToken) {
		return jwtTokenUtil.refreshHeadToken(oldToken);
	}

	@Override
	public List<UmsAdmin> list(String name, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		UmsAdminExample example = new UmsAdminExample();
		if(StringUtils.isEmpty(name)){
			example.createCriteria().andUsernameLike("%"+name+"%");
			example.or(example.createCriteria().andNickNameLike("%"+name+"%"));
		}
		List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
		return umsAdminList;
	}

	@Override
	public UmsAdmin get(Long adminId) {
		return umsAdminMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public int update(Long adminId, UmsAdmin umsAdmin) {
		umsAdmin.setId(adminId);
		umsAdmin.setPassword(null);
		return umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
	}

	@Override
	public int delete(Long adminId) {
		return umsAdminMapper.deleteByPrimaryKey(adminId);
	}

	@Override
	public int updateRole(Long adminId, List<Long> roleIds) {
		int count = roleIds==null?0:roleIds.size();
		//删除原来的关系
		UmsAdminRoleRelationExample example = new UmsAdminRoleRelationExample();
		example.createCriteria().andAdminIdEqualTo(adminId);
		umsAdminRoleRelationMapper.deleteByExample(example);
		
		if(!CollectionUtils.isEmpty(roleIds)){
			List<UmsAdminRoleRelation> relationList = new ArrayList<>();
			for(Long roleId:roleIds){
				UmsAdminRoleRelation relation = new UmsAdminRoleRelation();
				relation.setAdminId(adminId);
				relation.setRoleId(roleId);
				relationList.add(relation);
			}
			umsAdminRoleRelationDao.insertList(relationList);
		}
		return count;
		
	}

	@Override
	public int updatePassword(UpdateAdminPasswordParam param) {
		if(StrUtil.isEmpty(param.getUsername()) 
				|| StrUtil.isEmpty(param.getOldPassword())
				|| StrUtil.isEmpty(param.getNewPassword())){
		 return -1;
		}	
		UmsAdminExample example = new UmsAdminExample();
		example.createCriteria().andUsernameEqualTo(param.getUsername());
		List<UmsAdmin> adminList = umsAdminMapper.selectByExample(example);
		if(CollUtil.isEmpty(adminList)){
			return -2;
		}
		UmsAdmin admin =adminList.get(0);
		if(!passwordEncoder.matches(param.getOldPassword(), admin.getPassword())){
			return -3;
		}
		admin.setPassword(passwordEncoder.encode(param.getNewPassword()));
		umsAdminMapper.updateByPrimaryKey(admin);
		return 1;
	}

	@Override
	public List<UmsRole> getRoleList(Long adminId) {
		return umsAdminRoleRelationDao.getRoleList(adminId);
	}

	@Override
	public int updatePermission(Long adminId, List<Long> perIds) {
		UmsAdminPermissionRelationExample example = new UmsAdminPermissionRelationExample();
		example.createCriteria().andAdminIdEqualTo(adminId);
		umsAdminPermissionRelationMapper.deleteByExample(example);
		
		List<UmsPermission> perList = umsAdminRoleRelationDao.getPermissionList(adminId);
		List<Long> rolePerList = perList.stream().map(UmsPermission::getId).collect(Collectors.toList());
		
		if(!CollUtil.isEmpty(perIds)){
			List<Long> addPermissionList = perIds.stream().filter(perId -> !rolePerList.contains(perId)).collect(Collectors.toList());
			List<Long> subPermissionList = rolePerList.stream().filter(perId -> !perIds.contains(perId)).collect(Collectors.toList());
			
			List<UmsAdminPermissionRelation> relations = new ArrayList<>();
			relations.addAll(convert(adminId,1,addPermissionList));
			relations.addAll(convert(adminId,-1,subPermissionList));
			return umsAdminPermissionRelationDao.insertList(relations);
		}
		return 0;
		}

	/**
     * 将+-权限关系转化为对象
     */
    private List<UmsAdminPermissionRelation> convert(Long adminId,Integer type,List<Long> permissionIdList) {
        List<UmsAdminPermissionRelation> relationList = permissionIdList.stream().map(permissionId -> {
            UmsAdminPermissionRelation relation = new UmsAdminPermissionRelation();
            relation.setAdminId(adminId);
            relation.setType(type);
            relation.setPermissionId(permissionId);
            return relation;
        }).collect(Collectors.toList());
        return relationList;
    }
	
}
