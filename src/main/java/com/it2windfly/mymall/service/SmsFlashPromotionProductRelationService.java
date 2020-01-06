package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.SmsFlashPromotionProduct;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotionProductRelation;

public interface SmsFlashPromotionProductRelationService {

	int create(List<SmsFlashPromotionProductRelation> relationList);

	int update(Long id, SmsFlashPromotionProductRelation relation);

	int delete(Long id);

	List<SmsFlashPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize,
			Integer pageNum);

	SmsFlashPromotionProductRelation getItem(Long id);

	long getCount(Long flashPromotionId, Long flashPromotionSessionId);

}
