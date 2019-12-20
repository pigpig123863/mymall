package com.it2windfly.mymall.controller;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.it2windfly.mymall.common.api.CommonResult;
import com.it2windfly.mymall.dto.UmsAdminLoginParam;
import com.it2windfly.mymall.service.UmsAdminService;


@Controller
@RequestMapping("/admin")
public class UmsAdminController {
	@Autowired UmsAdminService adminService;
	@Value("${jwt.tokenHead}")
    private String tokenHead;
	
	@RequestMapping("/login")
	public CommonResult login(@RequestBody UmsAdminLoginParam adminLoginParam){
		String token = adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword());
		if(token == null){
			return CommonResult.validateFailed("用户名或密码错误");
		}
		
		Map tokenMap = new HashedMap();
		tokenMap.put("token", token);
		tokenMap.put("tokenhead",tokenHead);
		return CommonResult.success(tokenMap);
}
	
}