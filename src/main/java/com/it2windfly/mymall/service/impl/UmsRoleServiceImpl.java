package com.it2windfly.mymall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it2windfly.mymall.dao.UmsRolePermissionRelationDao;
import com.it2windfly.mymall.mbg.mapper.UmsRoleMapper;
import com.it2windfly.mymall.mbg.mapper.UmsRolePermissionRelationMapper;
import com.it2windfly.mymall.mbg.model.UmsPermission;
import com.it2windfly.mymall.mbg.model.UmsPermissionExample;
import com.it2windfly.mymall.mbg.model.UmsRole;
import com.it2windfly.mymall.mbg.model.UmsRoleExample;
import com.it2windfly.mymall.mbg.model.UmsRolePermissionRelation;
import com.it2windfly.mymall.mbg.model.UmsRolePermissionRelationExample;
import com.it2windfly.mymall.service.UmsRoleService;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {
	@Autowired UmsRoleMapper umsRoleMapper;
	@Autowired UmsRolePermissionRelationDao umsRolePermissionRelationDao;
	@Autowired UmsRolePermissionRelationMapper umsRolePermissionRelationMapper;
	
	@Override
	public int create(UmsRole role) {
		role.setCreateTime(new Date());
		role.setStatus(1);
		role.setAdminCount(0);
		role.setSort(0);
		return umsRoleMapper.insert(role);
	}

	@Override
	public int update(Long id, UmsRole role) {
		role.setId(id);
		return umsRoleMapper.updateByPrimaryKey(role);		
	}

	@Override
	public int delete(List<Long> ids) {
		UmsRoleExample example = new UmsRoleExample();
		example.createCriteria().andIdIn(ids);
		return umsRoleMapper.deleteByExample(example);
	}

	@Override
	public List<UmsPermission> getPermission(Long roleId) {
		return umsRolePermissionRelationDao.getPermissionList(roleId);
	}

	@Override
	public int updatePermission(Long roleId, List<Long> permissionIds) {
		UmsRolePermissionRelationExample example = new UmsRolePermissionRelationExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		umsRolePermissionRelationMapper.deleteByExample(example);
		
		List<UmsRolePermissionRelation> relations = new ArrayList<>();
		for(Long pid:permissionIds){
			UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
			relation.setId(pid);
			relation.setRoleId(roleId);
			relations.add(relation);
		}
		return umsRolePermissionRelationDao.insertList(relations);
	}

	@Override
	public List<UmsRole> list() {
		return umsRoleMapper.selectByExample(new UmsRoleExample());
	}



}
