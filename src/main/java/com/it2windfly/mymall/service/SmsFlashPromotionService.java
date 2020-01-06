package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.SmsFlashPromotion;

public interface SmsFlashPromotionService {

	int create(SmsFlashPromotion flashPromotion);

	int update(Long id, SmsFlashPromotion flashPromotion);

	SmsFlashPromotion getItem(Long id);

	int delete(Long id);

	List<SmsFlashPromotion> list(String keyword, Integer pageSize, Integer pageNum);

	int updateStatus(Long id, Integer status);

}
