package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.mbg.model.UmsAdminRoleRelation;
import com.it2windfly.mymall.mbg.model.UmsPermission;
import com.it2windfly.mymall.mbg.model.UmsRole;

public interface UmsAdminRoleRelationDao {

	List<UmsPermission> getPermissionList(@Param("adminId")Long adminId);

	void insertList(List<UmsAdminRoleRelation> relationList);

	List<UmsRole> getRoleList(Long adminId);
	
}
