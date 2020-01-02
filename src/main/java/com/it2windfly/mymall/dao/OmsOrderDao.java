package com.it2windfly.mymall.dao;

import java.util.List;

import com.it2windfly.mymall.dto.OmsOrderDeliveryParam;
import com.it2windfly.mymall.dto.OmsOrderDetail;
import com.it2windfly.mymall.dto.OmsOrderQueryParam;
import com.it2windfly.mymall.mbg.model.OmsOrder;

public interface OmsOrderDao {

	List<OmsOrder> getList(OmsOrderQueryParam queryParam);

	int delivery(List<OmsOrderDeliveryParam> deliveryParamList);

	OmsOrderDetail getDetail(Long id);

}
