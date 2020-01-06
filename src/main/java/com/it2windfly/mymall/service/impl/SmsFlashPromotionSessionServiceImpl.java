package com.it2windfly.mymall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it2windfly.mymall.dto.SmsFlashPromotionSessionDetail;
import com.it2windfly.mymall.mbg.mapper.SmsFlashPromotionSessionMapper;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotionSession;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotionSessionExample;
import com.it2windfly.mymall.service.SmsFlashPromotionProductRelationService;
import com.it2windfly.mymall.service.SmsFlashPromotionSessionService;

@Service
public class SmsFlashPromotionSessionServiceImpl implements SmsFlashPromotionSessionService{
	@Autowired SmsFlashPromotionSessionMapper sessionMapper;
	@Autowired SmsFlashPromotionProductRelationService relationService;
	
	@Override
	public int create(SmsFlashPromotionSession promotionSession) {
		promotionSession.setCreateTime(new Date());
		return sessionMapper.insert(promotionSession);
	}

	@Override
	public int update(Long id, SmsFlashPromotionSession promotionSession) {
		promotionSession.setId(id);
		return sessionMapper.updateByPrimaryKeySelective(promotionSession);
	}

	@Override
	public int updateStatus(Long id, Integer status) {
		SmsFlashPromotionSession promotionSession = new SmsFlashPromotionSession();
		promotionSession.setStatus(status);
		SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
		example.createCriteria().andIdEqualTo(id);
		return sessionMapper.updateByExample(promotionSession, example);
		
	}

	@Override
	public int delete(Long id) {
		return sessionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public SmsFlashPromotionSession getItem(Long id) {
		return sessionMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SmsFlashPromotionSession> list() {
		return sessionMapper.selectByExample(new SmsFlashPromotionSessionExample());
	}

	@Override
	public List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId) {
		List<SmsFlashPromotionSessionDetail> resList = new ArrayList<>();
		SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
		example.createCriteria().andStatusEqualTo(1);
		List<SmsFlashPromotionSession> list = sessionMapper.selectByExample(example);
		for(SmsFlashPromotionSession session : list){
			SmsFlashPromotionSessionDetail sessionDetail = new SmsFlashPromotionSessionDetail();
			BeanUtils.copyProperties(session, sessionDetail);
			long count = relationService.getCount(flashPromotionId,session.getId());
			sessionDetail.setProductCount(count);
			resList.add(sessionDetail);
		}
		return resList;
	}

}
