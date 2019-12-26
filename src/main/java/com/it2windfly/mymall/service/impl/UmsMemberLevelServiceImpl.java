package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it2windfly.mymall.mbg.mapper.UmsMemberLevelMapper;
import com.it2windfly.mymall.mbg.model.UmsMemberLevel;
import com.it2windfly.mymall.mbg.model.UmsMemberLevelExample;
import com.it2windfly.mymall.service.UmsMemberLevelService;

@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService{
	@Autowired UmsMemberLevelMapper umsMemberLevelMapper;
	
	@Override
	public List<UmsMemberLevel> list(int defaultStatus) {
		UmsMemberLevelExample example = new UmsMemberLevelExample();
		example.createCriteria().andDefaultStatusEqualTo(defaultStatus);
		return umsMemberLevelMapper.selectByExample(example);
	}

}
