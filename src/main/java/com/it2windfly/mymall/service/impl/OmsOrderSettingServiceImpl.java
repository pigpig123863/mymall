package com.it2windfly.mymall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it2windfly.mymall.mbg.mapper.OmsOrderSettingMapper;
import com.it2windfly.mymall.mbg.model.OmsOrderSetting;
import com.it2windfly.mymall.service.OmsOrderSettingService;

@Service
public class OmsOrderSettingServiceImpl implements OmsOrderSettingService {
	@Autowired OmsOrderSettingMapper orderSettingMapper;
	
	@Override
	public OmsOrderSetting get(Long id) {
		return orderSettingMapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(Long id, OmsOrderSetting orderSetting) {
		orderSetting.setId(id);
		return orderSettingMapper.updateByPrimaryKeySelective(orderSetting);
	}



}
