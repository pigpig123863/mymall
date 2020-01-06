package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.mbg.mapper.SmsCouponHistoryMapper;
import com.it2windfly.mymall.mbg.model.SmsCouponHistory;
import com.it2windfly.mymall.mbg.model.SmsCouponHistoryExample;
import com.it2windfly.mymall.service.SmsCouponHistoryService;

import cn.hutool.core.util.StrUtil;

@Service
public class SmsCouponHistoryServiceImpl implements SmsCouponHistoryService {
	@Autowired SmsCouponHistoryMapper couponHistoryMapper;
	
	@Override
	public List<SmsCouponHistory> list(Long couponId, Integer useStatus, String orderSn, Integer pageSize,
			Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsCouponHistoryExample example = new SmsCouponHistoryExample();
		SmsCouponHistoryExample.Criteria criteria = example.createCriteria();
		if(useStatus!=null){
			criteria.andUseStatusEqualTo(useStatus);
		}
		if(StrUtil.isEmpty(orderSn)){
			criteria.andOrderSnLike("%"+orderSn+"%");
		}
		if(couponId!=null){
            criteria.andCouponIdEqualTo(couponId);
        }
		return couponHistoryMapper.selectByExample(example);
	}

}
