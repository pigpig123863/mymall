package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.UmsAdminParam;
import com.it2windfly.mymall.mbg.model.UmsAdmin;
import com.it2windfly.mymall.mbg.model.UmsPermission;



public interface UmsAdminService {

	UmsAdmin getAdminByUsername(String username);

	String login(String username, String password);

	List<UmsPermission> getPermissionList(Long adminId);

	UmsAdmin register(UmsAdminParam umsAdminParam);

	String refreshToken(String token);

	List<UmsAdmin> list(String name, Integer pageSize, Integer pageNum);

	UmsAdmin get(Long adminId);

	int update(Long adminId, UmsAdmin umsAdmin);

	int delete(Long adminId);


}
