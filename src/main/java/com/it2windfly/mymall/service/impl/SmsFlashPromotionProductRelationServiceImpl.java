package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.dao.SmsFlashPromotionProductRelationDao;
import com.it2windfly.mymall.dto.SmsFlashPromotionProduct;
import com.it2windfly.mymall.mbg.mapper.SmsFlashPromotionProductRelationMapper;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotionProductRelation;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotionProductRelationExample;
import com.it2windfly.mymall.service.SmsFlashPromotionProductRelationService;

@Service
public class SmsFlashPromotionProductRelationServiceImpl implements SmsFlashPromotionProductRelationService{
	@Autowired SmsFlashPromotionProductRelationMapper relationMapper;
	@Autowired SmsFlashPromotionProductRelationDao relationDao;
	
	@Override
	public int create(List<SmsFlashPromotionProductRelation> relationList) {
		for(SmsFlashPromotionProductRelation relation : relationList){
			relationMapper.insert(relation);
		}
		return relationList.size();
	}

	@Override
	public int update(Long id, SmsFlashPromotionProductRelation relation) {
		relation.setId(id);
	    return relationMapper.updateByPrimaryKeySelective(relation);
	}

	@Override
	public int delete(Long id) {
		return relationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<SmsFlashPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize,
			Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return relationDao.getList(flashPromotionId,flashPromotionSessionId);
	}

	@Override
	public SmsFlashPromotionProductRelation getItem(Long id) {
		return relationMapper.selectByPrimaryKey(id);
	}

	@Override
	public long getCount(Long flashPromotionId, Long flashPromotionSessionId) {
		SmsFlashPromotionProductRelationExample example = new SmsFlashPromotionProductRelationExample();
		example.createCriteria().andFlashPromotionIdEqualTo(flashPromotionId).andFlashPromotionSessionIdEqualTo(flashPromotionSessionId);
		return relationMapper.countByExample(example);				
	}



}
