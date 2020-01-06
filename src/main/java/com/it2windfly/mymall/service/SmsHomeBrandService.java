package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.SmsHomeBrand;

public interface SmsHomeBrandService {

	int create(List<SmsHomeBrand> homeBrandList);

	int updateSort(Long id, Integer sort);

	int delete(List<Long> ids);

	int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

	List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
