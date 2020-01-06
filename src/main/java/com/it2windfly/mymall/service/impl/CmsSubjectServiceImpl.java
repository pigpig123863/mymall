package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.mbg.mapper.CmsSubjectMapper;
import com.it2windfly.mymall.mbg.model.CmsSubject;
import com.it2windfly.mymall.mbg.model.CmsSubjectExample;
import com.it2windfly.mymall.service.CmsSubjectService;

import cn.hutool.core.util.StrUtil;

@Service
public class CmsSubjectServiceImpl implements CmsSubjectService{
	@Autowired CmsSubjectMapper subjectMapper;
	
	
	@Override
	public List<CmsSubject> listAll() {
		return subjectMapper.selectByExample(new CmsSubjectExample());
	}

	@Override
	public List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		CmsSubjectExample example = new CmsSubjectExample();
		CmsSubjectExample.Criteria criteria = example.createCriteria();
		if(StrUtil.isEmpty(keyword)){
			criteria.andTitleLike("%"+keyword+"%");
		}
		return subjectMapper.selectByExample(example);
	}

}
