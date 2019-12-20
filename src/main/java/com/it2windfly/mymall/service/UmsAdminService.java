package com.it2windfly.mymall.service;

import java.util.List;



import com.it2windfly.mymall.mbg.model.UmsAdmin;
import com.it2windfly.mymall.mbg.model.UmsPermission;



public interface UmsAdminService {

	UmsAdmin getAdminByUsername(String username);

	List<UmsPermission> getPermissionList(Object id);

	String login(String username, String password);

	List<UmsPermission> getPermissionList(Long adminId);

}
