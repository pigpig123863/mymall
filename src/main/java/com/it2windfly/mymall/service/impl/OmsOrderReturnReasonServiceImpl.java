package com.it2windfly.mymall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.mbg.mapper.OmsOrderReturnReasonMapper;
import com.it2windfly.mymall.mbg.model.OmsOrderReturnReason;
import com.it2windfly.mymall.mbg.model.OmsOrderReturnReasonExample;
import com.it2windfly.mymall.service.OmsOrderReturnReasonService;

@Service
public class OmsOrderReturnReasonServiceImpl implements OmsOrderReturnReasonService {
	@Autowired OmsOrderReturnReasonMapper orderReturnReasonMapper;
	
	
	@Override
	public int create(OmsOrderReturnReason returnReason) {
		returnReason.setCreateTime(new Date());
		return orderReturnReasonMapper.insert(returnReason);
	}

	@Override
	public int update(Long id, OmsOrderReturnReason returnReason) {
		returnReason.setId(id);
		return orderReturnReasonMapper.updateByPrimaryKeySelective(returnReason);
	}

	@Override
	public int delete(List<Long> ids) {
		OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
		example.createCriteria().andIdIn(ids);
		return orderReturnReasonMapper.deleteByExample(example);
	}

	@Override
	public List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum,pageSize);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.setOrderByClause("sort desc");
        return orderReturnReasonMapper.selectByExample(example);
	}

	@Override
	public OmsOrderReturnReason getItem(Long id) {
		return orderReturnReasonMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateStatus(List<Long> ids, Integer status) {
		 if(!status.equals(0)&&!status.equals(1)){
	            return 0;
	        }
	        OmsOrderReturnReason record = new OmsOrderReturnReason();
	        record.setStatus(status);
	        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
	        example.createCriteria().andIdIn(ids);
	        return orderReturnReasonMapper.updateByExampleSelective(record,example);
	    }

	}

