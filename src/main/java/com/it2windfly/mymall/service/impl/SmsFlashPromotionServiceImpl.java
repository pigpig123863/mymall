package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.mbg.mapper.SmsFlashPromotionMapper;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotion;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotionExample;
import com.it2windfly.mymall.service.SmsFlashPromotionService;

import cn.hutool.core.util.StrUtil;

@Service
public class SmsFlashPromotionServiceImpl implements SmsFlashPromotionService{
	@Autowired SmsFlashPromotionMapper flashPromotionMapper;
	
	@Override
	public int create(SmsFlashPromotion flashPromotion) {
		return flashPromotionMapper.insert(flashPromotion);
	}

	@Override
	public int update(Long id, SmsFlashPromotion flashPromotion) {
		flashPromotion.setId(id);
		return flashPromotionMapper.updateByPrimaryKeySelective(flashPromotion);
	}

	@Override
	public SmsFlashPromotion getItem(Long id) {
		return flashPromotionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int delete(Long id) {
		return flashPromotionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<SmsFlashPromotion> list(String keyword, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsFlashPromotionExample example = new SmsFlashPromotionExample();
		SmsFlashPromotionExample.Criteria criteria = example.createCriteria();
		if(StrUtil.isEmpty(keyword)){
			criteria.andTitleLike("%"+keyword+"%");
		}
		example.setOrderByClause("sort desc");
		return flashPromotionMapper.selectByExample(example);
	}

	@Override
	public int updateStatus(Long id, Integer status) {
		SmsFlashPromotion flashPromotion = new SmsFlashPromotion();
        flashPromotion.setId(id);
        flashPromotion.setStatus(status);
        return flashPromotionMapper.updateByPrimaryKeySelective(flashPromotion);
	}



	

}
