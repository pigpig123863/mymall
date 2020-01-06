package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.SmsHomeRecommendProduct;

public interface SmsHomeRecommendProductService {

	int create(List<SmsHomeRecommendProduct> homeBrandList);

	int updateSort(Long id, Integer sort);

	int delete(List<Long> ids);

	int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

	List<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
