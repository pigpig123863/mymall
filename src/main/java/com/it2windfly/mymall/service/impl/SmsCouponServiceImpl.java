package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.dao.SmsCouponDao;
import com.it2windfly.mymall.dao.SmsCouponProductCategoryRelationDao;
import com.it2windfly.mymall.dao.SmsCouponProductRelationDao;
import com.it2windfly.mymall.dto.SmsCouponParam;
import com.it2windfly.mymall.mbg.mapper.SmsCouponMapper;
import com.it2windfly.mymall.mbg.mapper.SmsCouponProductCategoryRelationMapper;
import com.it2windfly.mymall.mbg.mapper.SmsCouponProductRelationMapper;
import com.it2windfly.mymall.mbg.model.*;
import com.it2windfly.mymall.service.SmsCouponService;

import cn.hutool.core.util.StrUtil;

@Service
public class SmsCouponServiceImpl implements SmsCouponService{
	@Autowired SmsCouponMapper couponMapper;
	@Autowired SmsCouponDao couponDao;
	@Autowired SmsCouponProductRelationDao productRelationDao;
	@Autowired SmsCouponProductRelationMapper productRelationMapper;
	@Autowired SmsCouponProductCategoryRelationMapper productCategoryRelationMapper;
	@Autowired SmsCouponProductCategoryRelationDao productCategoryRelationDao;
	
	@Override
	public int create(SmsCouponParam couponParam) {
		couponParam.setCount(couponParam.getPublishCount());
		couponParam.setUseCount(0);
		couponParam.setReceiveCount(0);
		int count = couponMapper.insert(couponParam);
		
		if(couponParam.getUseType()==2){
		for(SmsCouponProductCategoryRelation categoryRelation :couponParam.getProductCategoryRelationList()){
			categoryRelation.setCouponId(couponParam.getId());
		}
		productCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
		}
		
		if(couponParam.getUseType()==1){
		for(SmsCouponProductRelation productRelation:couponParam.getProductRelationList()){
			productRelation.setCouponId(couponParam.getId());
		}
		productRelationDao.insertList(couponParam.getProductRelationList());
		}
		return count;
	}

	@Override
	public int delete(Long id) {
		int count = couponMapper.deleteByPrimaryKey(id);
		deleteProductCategoryRelation(id);
		deleteProductRelation(id);
		return count;
	}

	private void deleteProductRelation(Long id) {
		SmsCouponProductRelationExample example = new SmsCouponProductRelationExample();
		example.createCriteria().andCouponIdEqualTo(id);
		productRelationMapper.deleteByExample(example);
		
	}

	private void deleteProductCategoryRelation(Long id) {
		SmsCouponProductCategoryRelationExample example = new SmsCouponProductCategoryRelationExample();
		example.createCriteria().andCouponIdEqualTo(id);
		productCategoryRelationMapper.deleteByExample(example);
		
	}

	@Override
	public int update(Long id, SmsCouponParam couponParam) {		
		couponParam.setId(id);
		int count = couponMapper.updateByPrimaryKeySelective(couponParam);
		
		if(couponParam.getUseType()==2){
			for(SmsCouponProductCategoryRelation categoryRelation :couponParam.getProductCategoryRelationList()){
				categoryRelation.setCouponId(couponParam.getId());
			}
			productCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
		}
		
		
		if(couponParam.getUseType()==1){
			for(SmsCouponProductRelation productRelation:couponParam.getProductRelationList()){
				productRelation.setCouponId(couponParam.getId());
			}
			productRelationDao.insertList(couponParam.getProductRelationList());
		}
		
		return count;
	}

	@Override
	public List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsCouponExample example = new SmsCouponExample();
		SmsCouponExample.Criteria criteria = example.createCriteria();
		if(StrUtil.isEmpty(name)){
			criteria.andNameLike("%"+name+"%");
		}
		if(type!=null){
			criteria.andTypeEqualTo(type);
		}
		return couponMapper.selectByExample(example);
	}

	@Override
	public SmsCouponParam getItem(Long id) {
		return couponDao.getItem(id);
	}

}
