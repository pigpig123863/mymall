package com.it2windfly.mymall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it2windfly.mymall.common.api.CommonResult;
import com.it2windfly.mymall.mbg.model.OmsOrderSetting;
import com.it2windfly.mymall.service.OmsOrderSettingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "OmsOrderSettingController", description = "订单设置管理")
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {
	@Autowired OmsOrderSettingService orderSettingService;
	
	@ApiOperation("获取指定订单设置")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OmsOrderSetting> get(@PathVariable Long id){
		OmsOrderSetting orderSetting = orderSettingService.get(id);
		return CommonResult.success(orderSetting);
	}
	
	@ApiOperation("修改指定订单设置")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody OmsOrderSetting orderSetting){
		int count = orderSettingService.update(id,orderSetting);
		if(count>0){
			return CommonResult.success(count);
		}else{
			return CommonResult.failed();
		}
	}
}
