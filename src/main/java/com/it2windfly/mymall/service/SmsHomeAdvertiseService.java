package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.SmsHomeAdvertise;

public interface SmsHomeAdvertiseService {

	int create(SmsHomeAdvertise advertise);

	int delete(List<Long> ids);

	int updateStatus(Long id, Integer status);

	SmsHomeAdvertise getItem(Long id);

	int update(Long id, SmsHomeAdvertise advertise);

	List<SmsHomeAdvertise> list(String name, Integer type, String endTime, Integer pageSize, Integer pageNum);

}
