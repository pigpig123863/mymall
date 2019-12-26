package com.it2windfly.mymall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it2windfly.mymall.common.api.CommonResult;
import com.it2windfly.mymall.mbg.model.UmsMemberLevel;
import com.it2windfly.mymall.service.UmsMemberLevelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="UmsMemberLevelController",description="会员等级管理")
@Controller
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {
	@Autowired UmsMemberLevelService umsMemberLevelService;
	
	@ApiOperation("查询所有会员等级")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<UmsMemberLevel>> getLevelList(@RequestParam("defaultStatus")int defaultStatus){
		List<UmsMemberLevel> levelList = umsMemberLevelService.list(defaultStatus);
		return CommonResult.success(levelList);
	}
}
