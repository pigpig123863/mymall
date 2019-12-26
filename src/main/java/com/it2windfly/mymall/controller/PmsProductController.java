package com.it2windfly.mymall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it2windfly.mymall.common.api.CommonPage;
import com.it2windfly.mymall.common.api.CommonResult;
import com.it2windfly.mymall.dto.PmsProductInfo;
import com.it2windfly.mymall.dto.PmsProductParam;
import com.it2windfly.mymall.dto.PmsProductQueryParam;
import com.it2windfly.mymall.mbg.model.PmsProduct;
import com.it2windfly.mymall.service.PmsProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags="PmsProductController",description="商品管理")
@RequestMapping("/product")
public class PmsProductController {
	@Autowired PmsProductService pmsProductService;
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	@ApiOperation("创建商品")
	@ResponseBody
	@PreAuthorize("hasAuthority('pms:product:create')")
	public CommonResult create(@RequestBody PmsProductParam pmsProductParam){
		int count = pmsProductService.create(pmsProductParam);
		if(count>0){
			return CommonResult.success(count);
		}else{
			return CommonResult.failed();
		}
	}
	
	@RequestMapping(value="/updateInfo/{id}",method=RequestMethod.POST)
	@ApiOperation("根据商品id获取商品编辑信息")
	@ResponseBody
	@PreAuthorize("hasAuthority('pms:product:read')")
	public CommonResult<PmsProductInfo>  getUpdateInfo(@PathVariable Long id){
		PmsProductInfo productInfo = pmsProductService.getUpdateInfo(id);
		return CommonResult.success(productInfo);
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ApiOperation("更新商品")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:product:update')")
	public CommonResult update(@PathVariable Long id,@RequestBody PmsProductParam pmsProductParam){
		int count = pmsProductService.update(id,pmsProductParam);
		if(count>0){
			return CommonResult.success(count);
		}else{
			return CommonResult.failed();
		}
	}
	
	@ApiOperation("查询商品")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:product:read')")
	public CommonResult<CommonPage<PmsProduct>> list(@RequestBody PmsProductQueryParam pmsProductQueryParam,
											   @RequestParam("pageNum")int pageNum,
											   @RequestParam("pageSize")int pageSize){
		List<PmsProduct> productList = pmsProductService.list(pmsProductQueryParam,pageNum,pageSize);
		return CommonResult.success(CommonPage.restPage(productList));
	}
}
