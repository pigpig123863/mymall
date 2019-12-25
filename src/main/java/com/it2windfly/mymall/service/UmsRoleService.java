package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.UmsPermission;
import com.it2windfly.mymall.mbg.model.UmsRole;

public interface UmsRoleService {

	int create(UmsRole role);

	int update(Long id, UmsRole role);

	int delete(List<Long> ids);

	List<UmsPermission> getPermission(Long roleId);

	int updatePermission(Long roleId, List<Long> permissionIds);

	List<UmsRole> list();
	
}
