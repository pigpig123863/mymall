package com.it2windfly.mymall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it2windfly.mymall.common.api.CommonPage;
import com.it2windfly.mymall.common.api.CommonResult;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotion;
import com.it2windfly.mymall.service.SmsFlashPromotionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "SmsFlashPromotionController", description = "限时购活动管理")
@RequestMapping("/flash")
public class SmsFlashPromotionController {
	@Autowired SmsFlashPromotionService flashPromotionService;
	
	@ApiOperation("添加活动")
	@RequestMapping(value="/create",method=RequestMethod.POST)
	@ResponseBody
	public CommonResult create(@RequestBody SmsFlashPromotion FlashPromotion){
		int count = flashPromotionService.create(FlashPromotion);
		if(count>0){
			return CommonResult.success(count);
		}else{
			return CommonResult.failed();
		}
	}
	@ApiOperation("编辑活动信息")
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public CommonResult update(@RequestBody SmsFlashPromotion FlashPromotion,@PathVariable Long id){
		int count = flashPromotionService.update(id,FlashPromotion);
		if(count>0){
			return CommonResult.success(count);
		}else{
			return CommonResult.failed();
		}
	}
	
	@ApiOperation("查看单个活动信息")
	@RequestMapping(value="/getItem/{id}",method=RequestMethod.GET)
	@ResponseBody
	public CommonResult<SmsFlashPromotion> getItem(@PathVariable Long id){
		SmsFlashPromotion promotion = flashPromotionService.getItem(id);
		return CommonResult.success(promotion);
	}
	
	@ApiOperation("删除活动信息")
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@PathVariable Long id){
		int count = flashPromotionService.delete(id);
		if(count>0){
			return CommonResult.success(count);
		}else{
			return CommonResult.failed();
		}
	}
	
	@ApiOperation("根据活动名称分页查询")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public CommonResult<CommonPage<SmsFlashPromotion>> list(@RequestParam(value = "keyword", required = false) String keyword,
				             		 				  		@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
				             		 				  		@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
		List<SmsFlashPromotion> list= flashPromotionService.list(keyword,pageSize,pageNum);
		return CommonResult.success(CommonPage.restPage(list));
		
	}
	
	@ApiOperation("修改活动上下线状态")
	@RequestMapping(value="/update/status/{id}",method=RequestMethod.POST)
	@ResponseBody
	public CommonResult updateStatus(@PathVariable Long id,@RequestParam("status")Integer status){
		int count = flashPromotionService.updateStatus(id,status);
		if(count>0){
			return CommonResult.success(count);
		}else{
			return CommonResult.failed();
		}
	}
	
}