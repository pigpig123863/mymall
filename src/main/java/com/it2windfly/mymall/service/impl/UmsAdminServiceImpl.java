package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.it2windfly.mymall.bo.AdminUserDetails;
import com.it2windfly.mymall.dao.UmsAdminRoleRelationDao;
import com.it2windfly.mymall.mbg.mapper.UmsAdminMapper;
import com.it2windfly.mymall.mbg.model.UmsAdmin;
import com.it2windfly.mymall.mbg.model.UmsAdminExample;
import com.it2windfly.mymall.mbg.model.UmsPermission;
import com.it2windfly.mymall.security.util.JwtTokenUtil;
import com.it2windfly.mymall.service.UmsAdminService;


@Service
public class UmsAdminServiceImpl implements UmsAdminService{
	@Autowired UmsAdminMapper umsAdminMapper;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired UmsAdminRoleRelationDao adminRoleRelationDao;

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
		
		
	}

	private UserDetails loadUserByUsername(String username) {
		UmsAdmin admin = getAdminByUsername(username);
		if (admin!=null){
		List<UmsPermission> upList = getPermissionList(admin.getId());
		return new AdminUserDetails(admin,upList);
		}
		throw new  UsernameNotFoundException("用户名或密码错误");
	}

}
