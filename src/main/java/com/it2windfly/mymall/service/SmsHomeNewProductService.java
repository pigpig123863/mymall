package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.SmsHomeNewProduct;

public interface SmsHomeNewProductService {

	int create(List<SmsHomeNewProduct> homeBrandList);

	int updateSort(Long id, Integer sort);

	int delete(List<Long> ids);

	int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

	List<SmsHomeNewProduct> list(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);


}
