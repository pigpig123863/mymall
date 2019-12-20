package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.mbg.model.UmsPermission;

public interface UmsAdminRoleRelationDao {

	List<UmsPermission> getPermissionList(@Param("adminId")Long adminId);
	
}
