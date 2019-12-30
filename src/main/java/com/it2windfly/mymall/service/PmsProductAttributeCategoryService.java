package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.PmsProductAttributeCategoryItem;
import com.it2windfly.mymall.mbg.model.PmsProductAttributeCategory;

public interface PmsProductAttributeCategoryService {

	int create(String name);

	int update(Long id, String name);

	int delete(Long id);

	PmsProductAttributeCategory get(Long id);

	List<PmsProductAttributeCategory> list(Integer pageSize, Integer pageNum);

	List<PmsProductAttributeCategoryItem> getListWithAttr();

}
