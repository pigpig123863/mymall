package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.mbg.mapper.SmsHomeRecommendProductMapper;
import com.it2windfly.mymall.mbg.model.*;
import com.it2windfly.mymall.service.SmsHomeRecommendProductService;



@Service
public class SmsHomeRecommendProductServiceImpl implements SmsHomeRecommendProductService{
	@Autowired SmsHomeRecommendProductMapper recommendProductMapper;
	
	@Override
	public int create(List<SmsHomeRecommendProduct> homeBrandList) {
		for(SmsHomeRecommendProduct recommendProduct : homeBrandList){
			recommendProduct.setSort(0);
			recommendProduct.setRecommendStatus(1);
			recommendProductMapper.insert(recommendProduct);
		}
		return homeBrandList.size();
	}

	@Override
	public int updateSort(Long id, Integer sort) {
		SmsHomeRecommendProduct recommendProduct = new SmsHomeRecommendProduct();
        recommendProduct.setId(id);
        recommendProduct.setSort(sort);
        return recommendProductMapper.updateByPrimaryKeySelective(recommendProduct);
	}

	@Override
	public int delete(List<Long> ids) {
		SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        example.createCriteria().andIdIn(ids);
        return recommendProductMapper.deleteByExample(example);
	}

	@Override
	public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
		SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        example.createCriteria().andIdIn(ids);
        SmsHomeRecommendProduct record = new SmsHomeRecommendProduct();
        record.setRecommendStatus(recommendStatus);
        return recommendProductMapper.updateByExampleSelective(record,example);
	}

	@Override
	public List<SmsHomeRecommendProduct> list(String productName, Integer recommendStatus, Integer pageSize,
			Integer pageNum) {
		PageHelper.startPage(pageNum,pageSize);
        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
        SmsHomeRecommendProductExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(productName)){
            criteria.andProductNameLike("%"+productName+"%");
        }
        if(recommendStatus!=null){
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        example.setOrderByClause("sort desc");
        return recommendProductMapper.selectByExample(example);
	}

}
