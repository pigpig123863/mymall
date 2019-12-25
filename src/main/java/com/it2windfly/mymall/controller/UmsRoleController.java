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

import com.it2windfly.mymall.common.api.CommonResult;
import com.it2windfly.mymall.mbg.model.UmsPermission;
import com.it2windfly.mymall.mbg.model.UmsRole;
import com.it2windfly.mymall.service.UmsRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="UmsRoleController",description="后台用户角色管理")
@RequestMapping("/role")
@Controller
public class UmsRoleController {
	@Autowired UmsRoleService umsRoleService;
	
	@ApiOperation(value="添加角色")
	@RequestMapping(value="/create",method=RequestMethod.POST)
	@ResponseBody
	public CommonResult create(@RequestBody UmsRole role){
		int count = umsRoleService.create(role);
		if(count==0){
			return CommonResult.failed();
		}
		return CommonResult.success(count);
	}
	
	@ApiOperation(value="修改角色")
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public CommonResult update(@PathVariable Long id, @RequestBody UmsRole role){
		int count = umsRoleService.update(id,role);
		if(count==0){
			return CommonResult.failed();
		}
		return CommonResult.success(count);
	}
	
	@ApiOperation(value="批量删除角色")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public CommonResult delete(@RequestParam("ids") List<Long> ids){
		int count = umsRoleService.delete(ids);
		if(count==0){
			return CommonResult.failed();
		}
		return CommonResult.success(count);
	}
	
	@ApiOperation(value="获取角色相应权限")
	@RequestMapping(value="/permisson/{roleId}",method=RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<UmsPermission>> getPerList(@PathVariable Long roleId){
		List<UmsPermission> perList = umsRoleService.getPermission(roleId);
		return CommonResult.success(perList);
	}
	
	@ApiOperation("修改角色权限")
    @RequestMapping(value = "/permission/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePerimission(@RequestParam("roleId")Long roleId,@RequestParam("permissionIds")List<Long> permissionIds ){
		int count = umsRoleService.updatePermission(roleId, permissionIds);
		if(count>0){
			return CommonResult.success(count);
		}else{
			return CommonResult.failed();
		}
	}
	
	@ApiOperation("获取所有角色")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public CommonResult roleList(){
		List<UmsRole> roleList =  umsRoleService.list();
		return CommonResult.success(roleList);
	}
}
