package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.OmsOrderReturnReason;

public interface OmsOrderReturnReasonService {

	int create(OmsOrderReturnReason returnReason);

	int update(Long id, OmsOrderReturnReason returnReason);

	int delete(List<Long> ids);

	List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum);

	OmsOrderReturnReason getItem(Long id);

	int updateStatus(List<Long> ids, Integer status);

}
