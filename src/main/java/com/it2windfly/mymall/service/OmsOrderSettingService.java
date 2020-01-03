package com.it2windfly.mymall.service;

import com.it2windfly.mymall.mbg.model.OmsOrderSetting;

public interface OmsOrderSettingService {

	OmsOrderSetting get(Long id);

	int update(Long id, OmsOrderSetting orderSetting);

}
