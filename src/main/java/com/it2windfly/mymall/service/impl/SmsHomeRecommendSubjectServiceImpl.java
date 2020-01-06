package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.mbg.mapper.SmsHomeRecommendSubjectMapper;
import com.it2windfly.mymall.mbg.model.*;
import com.it2windfly.mymall.service.SmsHomeRecommendSubjectService;


@Service
public class SmsHomeRecommendSubjectServiceImpl implements SmsHomeRecommendSubjectService{
	@Autowired SmsHomeRecommendSubjectMapper recommendSubjectMapper;
	
	@Override
	public int create(List<SmsHomeRecommendSubject> recommendSubjectList) {
		for(SmsHomeRecommendSubject recommendSubject : recommendSubjectList){
			recommendSubject.setSort(0);
			recommendSubject.setRecommendStatus(1);
			recommendSubjectMapper.insert(recommendSubject);
		}
		return recommendSubjectList.size();
	}

	@Override
	public int updateSort(Long id, Integer sort) {
		SmsHomeRecommendSubject recommendProduct = new SmsHomeRecommendSubject();
        recommendProduct.setId(id);
        recommendProduct.setSort(sort);
        return recommendSubjectMapper.updateByPrimaryKeySelective(recommendProduct);
	}

	@Override
	public int delete(List<Long> ids) {
		SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
        example.createCriteria().andIdIn(ids);
        return recommendSubjectMapper.deleteByExample(example);
	}

	@Override
	public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
		SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
        example.createCriteria().andIdIn(ids);
        SmsHomeRecommendSubject record = new SmsHomeRecommendSubject();
        record.setRecommendStatus(recommendStatus);
        return recommendSubjectMapper.updateByExampleSelective(record,example);
	}

	@Override
	public List<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Integer pageSize,
			Integer pageNum) {
		PageHelper.startPage(pageNum,pageSize);
        SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
        SmsHomeRecommendSubjectExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(subjectName)){
            criteria.andSubjectNameLike("%"+subjectName+"%");
        }
        if(recommendStatus!=null){
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        example.setOrderByClause("sort desc");
        return recommendSubjectMapper.selectByExample(example);
    }
}

