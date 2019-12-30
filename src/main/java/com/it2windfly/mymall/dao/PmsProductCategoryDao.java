package com.it2windfly.mymall.dao;

import java.util.List;

import com.it2windfly.mymall.dto.PmsProductCategoryWithChildrenItem;

public interface PmsProductCategoryDao {

	List<PmsProductCategoryWithChildrenItem> listWithChildren();

}
