package com.it2windfly.mymall.dao;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.mbg.model.UmsAdminPermissionRelation;

import java.util.List;

/**
 * 用户权限自定义Dao
 * Created by macro on 2018/10/8.
 */
public interface UmsAdminPermissionRelationDao {
    int insertList(@Param("list") List<UmsAdminPermissionRelation> list);
}

