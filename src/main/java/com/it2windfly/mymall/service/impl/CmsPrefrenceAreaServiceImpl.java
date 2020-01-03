package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it2windfly.mymall.mbg.mapper.CmsPrefrenceAreaMapper;
import com.it2windfly.mymall.mbg.model.CmsPrefrenceArea;
import com.it2windfly.mymall.mbg.model.CmsPrefrenceAreaExample;
import com.it2windfly.mymall.service.CmsPrefrenceAreaService;

@Service
public class CmsPrefrenceAreaServiceImpl implements CmsPrefrenceAreaService{
	@Autowired CmsPrefrenceAreaMapper prefrenceAreaMapper;
	
	@Override
	public List<CmsPrefrenceArea> listAll() {
		return prefrenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
	}

}
