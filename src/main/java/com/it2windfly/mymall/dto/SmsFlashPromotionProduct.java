package com.it2windfly.mymall.dto;

import com.it2windfly.mymall.mbg.model.PmsProduct;
import com.it2windfly.mymall.mbg.model.SmsFlashPromotionProductRelation;

import lombok.Getter;
import lombok.Setter;

/**
 * 限时购及商品信息封装
 */
public class SmsFlashPromotionProduct extends SmsFlashPromotionProductRelation{
    @Getter
    @Setter
    private PmsProduct product;
}
