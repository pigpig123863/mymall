package com.it2windfly.mymall.dto;

import com.it2windfly.mymall.mbg.model.SmsFlashPromotionSession;

import lombok.Getter;
import lombok.Setter;

/**
 * 包含商品数量的场次信息
 */
public class SmsFlashPromotionSessionDetail extends SmsFlashPromotionSession {
    @Setter
    @Getter
    private Long productCount;

	public void setProductCount(long count) {
		// TODO Auto-generated method stub
		
	}
}

