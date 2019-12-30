package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.dto.ProductAttrInfo;

public interface PmsProductAttributeDao {

	List<ProductAttrInfo> getProductAttrInfo(@Param("id")Long productCategoryId);

}
