package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.OmsMoneyInfoParam;
import com.it2windfly.mymall.dto.OmsOrderDeliveryParam;
import com.it2windfly.mymall.dto.OmsOrderDetail;
import com.it2windfly.mymall.dto.OmsOrderQueryParam;
import com.it2windfly.mymall.dto.OmsReceiverInfoParam;
import com.it2windfly.mymall.mbg.model.OmsOrder;

public interface OmsOrderService {

	List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum);

	int delivery(List<OmsOrderDeliveryParam> deliveryParamList);

	int close(List<Long> ids, String note);

	int delete(List<Long> ids);

	OmsOrderDetail getDetail(Long id);

	int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam);

	int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam);

	int updateNote(Long id, String note, Integer status);

}
