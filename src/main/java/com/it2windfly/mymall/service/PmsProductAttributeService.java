package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.PmsProductAttributeParam;
import com.it2windfly.mymall.dto.ProductAttrInfo;
import com.it2windfly.mymall.mbg.model.*;

public interface PmsProductAttributeService {

	List<PmsProductAttribute> List(Long cid, Integer type, Integer pageSize, Integer pageNum);

	int create(PmsProductAttributeParam productAttributeParam);

	int update(Long id, PmsProductAttributeParam productAttributeParam);

	PmsProductAttribute get(Long id);

	int delete(List<Long> ids);

	List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);

}
