package com.it2windfly.mymall.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 修改订单费用信息参数
 */
@Getter
@Setter
public class OmsMoneyInfoParam {
    private Long orderId;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private Integer status;
	public Long getOrderId() {
		// TODO Auto-generated method stub
		return null;
	}
	public BigDecimal getFreightAmount() {
		// TODO Auto-generated method stub
		return null;
	}
	public BigDecimal getDiscountAmount() {
		// TODO Auto-generated method stub
		return null;
	}
	public Integer getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
}
