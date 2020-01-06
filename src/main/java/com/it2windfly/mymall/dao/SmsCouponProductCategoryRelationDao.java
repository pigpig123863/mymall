package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.mbg.model.SmsCouponProductCategoryRelation;

public interface SmsCouponProductCategoryRelationDao {

	int insertList(@Param("list")List<SmsCouponProductCategoryRelation> productCategoryRelationList);

}
