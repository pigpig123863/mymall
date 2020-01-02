package com.it2windfly.mymall.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单修改收货人信息参数
 */
@Getter
@Setter
public class OmsReceiverInfoParam {
    private Long orderId;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverDetailAddress;
    private String receiverProvince;
    private String receiverCity;
    private String receiverRegion;
    private Integer status;
	public Integer getStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getOrderId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getReceiverName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getReceiverPhone() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getReceiverDetailAddress() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getReceiverPostCode() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getReceiverCity() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getReceiverProvince() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getReceiverRegion() {
		// TODO Auto-generated method stub
		return null;
	}
}

