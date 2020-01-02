package com.it2windfly.mymall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.dao.OmsOrderDao;
import com.it2windfly.mymall.dao.OmsOrderOperateHistoryDao;
import com.it2windfly.mymall.dto.OmsMoneyInfoParam;
import com.it2windfly.mymall.dto.OmsOrderDeliveryParam;
import com.it2windfly.mymall.dto.OmsOrderDetail;
import com.it2windfly.mymall.dto.OmsOrderQueryParam;
import com.it2windfly.mymall.dto.OmsReceiverInfoParam;
import com.it2windfly.mymall.mbg.mapper.OmsOrderMapper;
import com.it2windfly.mymall.mbg.mapper.OmsOrderOperateHistoryMapper;
import com.it2windfly.mymall.mbg.model.OmsOrder;
import com.it2windfly.mymall.mbg.model.OmsOrderExample;
import com.it2windfly.mymall.mbg.model.OmsOrderOperateHistory;
import com.it2windfly.mymall.service.OmsOrderService;

@Service
public class OmsOrderServiceImpl implements OmsOrderService {
	@Autowired OmsOrderDao orderDao;
	@Autowired OmsOrderMapper orderMapper;
	@Autowired OmsOrderOperateHistoryDao orderOperateHistoryDao;
	@Autowired OmsOrderOperateHistoryMapper orderOperateHistoryMapper;
	
	@Override
	public List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return orderDao.getList(queryParam);
	}

	@Override
	public int delivery(List<OmsOrderDeliveryParam> deliveryParamList) {
		//批量发货
		int count = orderDao.delivery(deliveryParamList);
		//添加操作记录
		List<OmsOrderOperateHistory> historyList = deliveryParamList.stream()
				.map(OmsOrderDeliveryParam ->{
					OmsOrderOperateHistory history = new OmsOrderOperateHistory();
					history.setCreateTime(new Date());
					history.setOrderId(OmsOrderDeliveryParam.getOrderId());
					history.setOperateMan("后台管理员");
					history.setOrderStatus(2);
					history.setNote("已发货");
					return history;
				}).collect(Collectors.toList());
		return count;
	}

	@Override
	public int close(List<Long> ids, String note) {
		OmsOrder order = new OmsOrder();
		order.setStatus(4);
		OmsOrderExample example = new OmsOrderExample();
		example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
		int count = orderMapper.updateByExampleSelective(order, example);
		List<OmsOrderOperateHistory> historyList = ids.stream().map(orderId -> {
            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderId(orderId);
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setOrderStatus(4);
            history.setNote("订单关闭:"+note);
            return history;
        }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(historyList);
        return count;
	}

	@Override
	public int delete(List<Long> ids) {
		OmsOrder order = new OmsOrder();
		order.setDeleteStatus(1);
		OmsOrderExample example = new OmsOrderExample();
		example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
		return orderMapper.updateByExample(order, example);
	}

	@Override
	public OmsOrderDetail getDetail(Long id) {
		return orderDao.getDetail(id);
	}

	@Override
	public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
		OmsOrder order = new OmsOrder();
		order.setId(receiverInfoParam.getOrderId());
		order.setReceiverCity(receiverInfoParam.getReceiverCity());
		order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
		order.setReceiverName(receiverInfoParam.getReceiverName());
		order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
		order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
		order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
		order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(receiverInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        orderOperateHistoryMapper.insert(history);
        return count;
	}

	@Override
	public int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam) {
		OmsOrder order = new OmsOrder();
        order.setId(moneyInfoParam.getOrderId());
        order.setFreightAmount(moneyInfoParam.getFreightAmount());
        order.setDiscountAmount(moneyInfoParam.getDiscountAmount());
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(moneyInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(moneyInfoParam.getStatus());
        history.setNote("修改费用信息");
        orderOperateHistoryMapper.insert(history);
        return count;
	}

	@Override
	public int updateNote(Long id, String note, Integer status) {
		OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(id);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息："+note);
        orderOperateHistoryMapper.insert(history);
        return count;
	}

}
