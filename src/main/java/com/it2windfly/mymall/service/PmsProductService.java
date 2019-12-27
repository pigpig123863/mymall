package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.PmsProductInfo;
import com.it2windfly.mymall.dto.PmsProductParam;
import com.it2windfly.mymall.dto.PmsProductQueryParam;
import com.it2windfly.mymall.mbg.model.PmsProduct;

public interface PmsProductService {

	int create(PmsProductParam pmsProductParam);

	PmsProductInfo getUpdateInfo(Long id);

	int update(Long id, PmsProductParam pmsProductParam);

	List<PmsProduct> list(PmsProductQueryParam pmsProductQueryParam, int pageNum, int pageSize);

	List<PmsProduct> list(String keyword);

	int updateVerifyStatus(List<Long> ids, Integer vertifyStatus, String detail);

	int updatePublishStatus(List<Long> ids, Integer publishStatus);

	int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

	int updateNewStatus(List<Long> ids, Integer newStatus);

	int updateDeleteStatus(List<Long> ids, Integer deleteStatus);


}
