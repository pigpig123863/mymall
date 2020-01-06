package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.SmsFlashPromotionSessionDetail;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotionSession;

public interface SmsFlashPromotionSessionService {

	int create(SmsFlashPromotionSession promotionSession);

	int update(Long id, SmsFlashPromotionSession promotionSession);

	int updateStatus(Long id, Integer status);

	int delete(Long id);

	SmsFlashPromotionSession getItem(Long id);

	List<SmsFlashPromotionSession> list();

	List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId);

}
