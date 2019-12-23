package com.it2windfly.mymall.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it2windfly.mymall.common.api.CommonPage;
import com.it2windfly.mymall.common.api.CommonResult;
import com.it2windfly.mymall.dto.UmsAdminLoginParam;
import com.it2windfly.mymall.dto.UmsAdminParam;
import com.it2windfly.mymall.mbg.model.UmsAdmin;
import com.it2windfly.mymall.service.UmsAdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@Api(tags="UmsAdminController",description="后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
	@Autowired UmsAdminService umsAdminService;
	@Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
	
	
	@ApiOperation(value="用户登录")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public CommonResult login(@RequestBody UmsAdminLoginParam adminLoginParam){
		String token = umsAdminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword());
		if(token == null){
			return CommonResult.validateFailed("用户名或密码错误");
		}
		
		Map<String, String> tokenMap = new HashedMap();
		tokenMap.put("token", token);
		tokenMap.put("tokenhead",tokenHead);
		return CommonResult.success(tokenMap);
	}
	
	@ApiOperation(value="用户注册")
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public CommonResult<UmsAdmin> register(@RequestBody UmsAdminParam umsAdminParam){
		UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
		if(umsAdmin == null){
			return CommonResult.failed();
		}
		return CommonResult.success(umsAdmin);
	}
	
	@ApiOperation(value="刷新token")
	@RequestMapping(value="/refreshToken",method=RequestMethod.GET)
	@ResponseBody
	public CommonResult refreshToken(HttpServletRequest request){
		String token = request.getHeader(tokenHeader);
		String refreshToken = umsAdminService.refreshToken(token);
		if(refreshToken==null){
			return CommonResult.failed("token已经过期！");
		}
		Map<String,String> tokenMap = new HashMap<>();
		tokenMap.put("token", refreshToken);
		tokenMap.put("tokenHead",tokenHead);
		return CommonResult.success(tokenMap);
	}
	
	 @ApiOperation(value = "获取当前登录用户信息")
     @RequestMapping(value = "/info", method = RequestMethod.GET)
     @ResponseBody
     public CommonResult getAdminInfo(Principal principal){
		 String userName = principal.getName();
		 UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(userName);
		 Map<String,Object> infoMap = new HashMap<>();
		 infoMap.put("userName", userName);
		 infoMap.put("roles", new String[]{"TEST"});
		 infoMap.put("icon", umsAdmin.getIcon());
		 return CommonResult.success(infoMap);
	 }
	 
	 @ApiOperation(value = "登出功能")
     @RequestMapping(value = "/logout", method = RequestMethod.POST)
     @ResponseBody
     public CommonResult logout(){
		 return CommonResult.success(null);
	 }
     
	 @ApiOperation(value = "根据用户名或姓名分页获取用户列表")
     @RequestMapping(value = "/list", method = RequestMethod.GET)
     @ResponseBody
     public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(value = "name", required = false) String name,
										            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
										            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
		 List<UmsAdmin> adminList = umsAdminService.list(name,pageSize,pageNum);
		 
		 return CommonResult.success(CommonPage.restPage(adminList));
	 }
	 
	 @ApiOperation("获取指定用户信息")
     @RequestMapping(value = "/{id}", method = RequestMethod.GET)
     @ResponseBody
     public CommonResult getAdminInfo(@RequestParam("id")Long adminId){
		 UmsAdmin umsAdmin =umsAdminService.get(adminId);
		 return CommonResult.success(umsAdmin);
	 }
	 
	 @ApiOperation("修改指定用户信息")
     @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
     @ResponseBody
     public CommonResult updateAdminInfo(@RequestParam("id")Long adminId,@RequestBody UmsAdmin umsAdmin){
		 int count = umsAdminService.update(adminId,umsAdmin);
		 if(count!=0){
			 return CommonResult.success(count) ;
		 }
		 return CommonResult.failed();
	 }
	 
	 @ApiOperation("删除指定用户")
     @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
     @ResponseBody
     public CommonResult delete(@RequestParam("id")Long adminId){
		 int count = umsAdminService.delete(adminId);
		 if(count!=0){
			 return CommonResult.success(count);
		 }
		 return CommonResult.failed();
	 }
     
     
     
}