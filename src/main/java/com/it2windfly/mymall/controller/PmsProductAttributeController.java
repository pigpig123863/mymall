package com.it2windfly.mymall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it2windfly.mymall.common.api.CommonPage;
import com.it2windfly.mymall.common.api.CommonResult;
import com.it2windfly.mymall.dto.PmsProductAttributeParam;
import com.it2windfly.mymall.dto.ProductAttrInfo;
import com.it2windfly.mymall.mbg.model.*;
import com.it2windfly.mymall.service.PmsProductAttributeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "PmsProductAttributeController", description = "商品属性管理")
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
	 @Autowired PmsProductAttributeService productAttributeService;

     @ApiOperation("根据分类查询属性列表或参数列表")
     @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "0表示属性，1表示参数", required = true, paramType = "query", dataType = "integer")})
     @RequestMapping(value = "/list/{cid}", method = RequestMethod.GET)
     @ResponseBody
     public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable Long cid,
                                                                 @RequestParam(value = "type") Integer type,
                                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
         List<PmsProductAttribute> productAttributeList = productAttributeService.List(cid, type, pageSize, pageNum);
         return CommonResult.success(CommonPage.restPage(productAttributeList));
    }
     
     @ApiOperation("添加商品属性信息")
     @RequestMapping(value = "/create", method = RequestMethod.POST)
     @ResponseBody
     public CommonResult create(@RequestBody PmsProductAttributeParam productAttributeParam) {
         int count = productAttributeService.create(productAttributeParam);
         if (count > 0) {
             return CommonResult.success(count);
         } else {
             return CommonResult.failed();
         }
     }
     
     @ApiOperation("修改商品属性信息")
     @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
     @ResponseBody
     public CommonResult update(@PathVariable Long id, @RequestBody PmsProductAttributeParam productAttributeParam, BindingResult bindingResult) {
         int count = productAttributeService.update(id, productAttributeParam);
         if (count > 0) {
             return CommonResult.success(count);
         } else {
             return CommonResult.failed();
         }
     }
     
     @ApiOperation("查询单个商品属性")
     @RequestMapping(value = "/{id}", method = RequestMethod.GET)
     @ResponseBody
     public CommonResult<PmsProductAttribute> getItem(@PathVariable Long id) {
         PmsProductAttribute productAttribute = productAttributeService.get(id);
         return CommonResult.success(productAttribute);
     }
     
     @ApiOperation("批量删除商品属性")
     @RequestMapping(value = "/delete", method = RequestMethod.POST)
     @ResponseBody
     public CommonResult delete(@RequestParam("ids") List<Long> ids) {
         int count = productAttributeService.delete(ids);
         if (count > 0) {
             return CommonResult.success(count);
         } else {
             return CommonResult.failed();
         }
     }
     
     @ApiOperation("根据商品分类的id获取商品属性及属性分类")
     @RequestMapping(value = "/attrInfo/{productCategoryId}", method = RequestMethod.GET)
     @ResponseBody
     public CommonResult<List<ProductAttrInfo>> getAttrInfo(@PathVariable Long productCategoryId) {
         List<ProductAttrInfo> productAttrInfoList = productAttributeService.getProductAttrInfo(productCategoryId);
         return CommonResult.success(productAttrInfoList);
     }
}
