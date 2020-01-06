package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.dto.SmsFlashPromotionProduct;

public interface SmsFlashPromotionProductRelationDao {

	List<SmsFlashPromotionProduct> getList(@Param("flashPromotionId")Long flashPromotionId, @Param("flashPromotionSessionId")Long flashPromotionSessionId);

}
