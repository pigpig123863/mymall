package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.mbg.mapper.SmsHomeBrandMapper;
import com.it2windfly.mymall.mbg.model.SmsHomeBrand;
import com.it2windfly.mymall.mbg.model.SmsHomeBrandExample;
import com.it2windfly.mymall.service.SmsHomeBrandService;

@Service
public class SmsHomeBrandServiceImpl implements SmsHomeBrandService{
	@Autowired SmsHomeBrandMapper homeBrandMapper;
	
	@Override
	public int create(List<SmsHomeBrand> homeBrandList) {
		int count = 0;
		for(SmsHomeBrand homeBrand:homeBrandList){
			homeBrand.setRecommendStatus(1);
			homeBrand.setSort(0);
			homeBrandMapper.insert(homeBrand);
			count+=1;
		}
		return count;
	}

	@Override
	public int updateSort(Long id, Integer sort) {
		SmsHomeBrand homeBrand = new SmsHomeBrand();
		homeBrand.setSort(sort);
		SmsHomeBrandExample example = new SmsHomeBrandExample();
		example.createCriteria().andBrandIdEqualTo(id);
		return homeBrandMapper.updateByExample(homeBrand, example);
	}

	@Override
	public int delete(List<Long> ids) {
		SmsHomeBrandExample example = new SmsHomeBrandExample();
        example.createCriteria().andIdIn(ids);
        return homeBrandMapper.deleteByExample(example);
	}

	@Override
	public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
		SmsHomeBrandExample example = new SmsHomeBrandExample();
        example.createCriteria().andIdIn(ids);
        SmsHomeBrand record = new SmsHomeBrand();
        record.setRecommendStatus(recommendStatus);
        return homeBrandMapper.updateByExampleSelective(record,example);
	}

	@Override
	public List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum,pageSize);
        SmsHomeBrandExample example = new SmsHomeBrandExample();
        SmsHomeBrandExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(brandName)){
            criteria.andBrandNameLike("%"+brandName+"%");
        }
        if(recommendStatus!=null){
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        example.setOrderByClause("sort desc");
        return homeBrandMapper.selectByExample(example);
	}

}
