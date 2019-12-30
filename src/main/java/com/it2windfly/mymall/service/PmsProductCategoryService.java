package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.PmsProductCategoryParam;
import com.it2windfly.mymall.dto.PmsProductCategoryWithChildrenItem;
import com.it2windfly.mymall.mbg.model.PmsProductCategory;

public interface PmsProductCategoryService {

	int create(PmsProductCategoryParam productCategoryParam);

	int update(Long id, PmsProductCategoryParam productCategoryParam);

	List<PmsProductCategory> list(Long parentId, Integer pageSize, Integer pageNum);

	PmsProductCategory get(Long id);

	int delete(Long id);

	int updateNavStatus(List<Long> ids, Integer navStatus);

	int updateShowStatus(List<Long> ids, Integer showStatus);

	List<PmsProductCategoryWithChildrenItem> listWithChildren();

}
