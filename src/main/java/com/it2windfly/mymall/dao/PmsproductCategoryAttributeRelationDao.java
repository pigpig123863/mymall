package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.mbg.model.PmsProductCategoryAttributeRelation;

public interface PmsproductCategoryAttributeRelationDao {

	int insertList(@Param("list")List<PmsProductCategoryAttributeRelation> relationList);
	
}
