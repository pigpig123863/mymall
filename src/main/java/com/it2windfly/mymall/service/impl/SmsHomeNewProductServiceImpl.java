package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.mbg.mapper.SmsHomeNewProductMapper;
import com.it2windfly.mymall.mbg.model.SmsHomeNewProduct;
import com.it2windfly.mymall.mbg.model.SmsHomeNewProductExample;
import com.it2windfly.mymall.mbg.model.SmsHomeNewProductExample.Criteria;
import com.it2windfly.mymall.service.SmsHomeNewProductService;

import cn.hutool.core.util.StrUtil;

@Service
public class SmsHomeNewProductServiceImpl implements SmsHomeNewProductService{
	@Autowired SmsHomeNewProductMapper homeNewProductMapper;
	
	@Override
	public int create(List<SmsHomeNewProduct> homeBrandList) {
		for(SmsHomeNewProduct homeNewProduct:homeBrandList){
			homeNewProduct.setRecommendStatus(1);
			homeNewProduct.setSort(0);
			homeNewProductMapper.insert(homeNewProduct);
		}
		return homeBrandList.size();
	}

	@Override
	public int updateSort(Long id, Integer sort) {
		SmsHomeNewProduct homeNewProduct =new SmsHomeNewProduct();
		homeNewProduct.setSort(sort);
		homeNewProduct.setId(id);
		return homeNewProductMapper.updateByPrimaryKeySelective(homeNewProduct);
	}
	@Override
	public int delete(List<Long> ids) {
		SmsHomeNewProductExample example = new SmsHomeNewProductExample();
		example.createCriteria().andIdIn(ids);
		return homeNewProductMapper.deleteByExample(example);
	}

	@Override
	public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
		SmsHomeNewProduct homeNewProduct = new SmsHomeNewProduct();
		homeNewProduct.setRecommendStatus(recommendStatus);
		SmsHomeNewProductExample example = new SmsHomeNewProductExample();
		example.createCriteria().andIdIn(ids);
		return homeNewProductMapper.updateByExample(homeNewProduct, example);
	}

	@Override
	public List<SmsHomeNewProduct> list(String productName, Integer recommendStatus, Integer pageSize,
			Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsHomeNewProductExample example = new SmsHomeNewProductExample();
		SmsHomeNewProductExample.Criteria criteria = example.createCriteria();
		if(StrUtil.isEmpty(productName)){
			criteria.andProductNameLike("%"+productName+"%");
		}
		if(recommendStatus!=null){
			criteria.andRecommendStatusEqualTo(recommendStatus);
		}
		example.setOrderByClause("sort desc");
		return homeNewProductMapper.selectByExample(example);
	}

	
}
