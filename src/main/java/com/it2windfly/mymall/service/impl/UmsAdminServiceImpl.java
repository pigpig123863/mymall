package com.it2windfly.mymall.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.it2windfly.mymall.dao.UmsAdminRoleRelationDao;
import com.it2windfly.mymall.dto.UmsAdminParam;
import com.it2windfly.mymall.mbg.mapper.UmsAdminLoginLogMapper;
import com.it2windfly.mymall.mbg.mapper.UmsAdminMapper;
import com.it2windfly.mymall.mbg.model.UmsAdmin;
import com.it2windfly.mymall.mbg.model.UmsAdminExample;
import com.it2windfly.mymall.mbg.model.UmsAdminLoginLog;
import com.it2windfly.mymall.mbg.model.UmsPermission;
import com.it2windfly.mymall.security.utils.JwtTokenUtil;
import com.it2windfly.mymall.service.UmsAdminService;


@Service
public class UmsAdminServiceImpl implements UmsAdminService{
	private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
	@Autowired UmsAdminMapper umsAdminMapper;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired UmsAdminRoleRelationDao adminRoleRelationDao;
	@Autowired UmsAdminLoginLogMapper umsAdminLoginLogMapper;

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
		return adminRoleRelationDao.getPermissionList(adminId);
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
	
}
