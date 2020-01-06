package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.SmsCouponParam;
import com.it2windfly.mymall.mbg.model.SmsCoupon;

public interface SmsCouponService {

	int create(SmsCouponParam couponParam);

	int delete(Long id);

	int update(Long id, SmsCouponParam couponParam);

	List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum);

	SmsCouponParam getItem(Long id);

}
