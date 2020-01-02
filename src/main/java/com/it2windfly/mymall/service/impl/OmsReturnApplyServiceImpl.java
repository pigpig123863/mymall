package com.it2windfly.mymall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.dao.OmsOrderReturnApplyDao;
import com.it2windfly.mymall.dto.OmsOrderReturnApplyResult;
import com.it2windfly.mymall.dto.OmsReturnApplyQueryParam;
import com.it2windfly.mymall.dto.OmsUpdateStatusParam;
import com.it2windfly.mymall.mbg.mapper.OmsOrderReturnApplyMapper;
import com.it2windfly.mymall.mbg.model.OmsOrderReturnApply;
import com.it2windfly.mymall.mbg.model.OmsOrderReturnApplyExample;
import com.it2windfly.mymall.service.OmsReturnApplyService;

@Service
public class OmsReturnApplyServiceImpl implements OmsReturnApplyService{
	@Autowired OmsOrderReturnApplyMapper orderReturnApplyMapper;
	@Autowired OmsOrderReturnApplyDao orderReturnApplyDao;
	
	@Override
	public List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return orderReturnApplyDao.getList(queryParam);
	}

	@Override
	public int delete(List<Long> ids) {
		OmsOrderReturnApplyExample example = new OmsOrderReturnApplyExample();
		example.createCriteria().andIdIn(ids).andStatusEqualTo(3);
		return orderReturnApplyMapper.deleteByExample(example);
	}

	@Override
	public OmsOrderReturnApplyResult getItem(Long id) {
		return orderReturnApplyDao.getDetail(id);
	}

	@Override
	public int updateStatus(Long id, OmsUpdateStatusParam statusParam) {
		 Integer status = statusParam.getStatus();
	        OmsOrderReturnApply returnApply = new OmsOrderReturnApply();
	        if(status.equals(1)){
	            //确认退货
	            returnApply.setId(id);
	            returnApply.setStatus(1);
	            returnApply.setReturnAmount(statusParam.getReturnAmount());
	            returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
	            returnApply.setHandleTime(new Date());
	            returnApply.setHandleMan(statusParam.getHandleMan());
	            returnApply.setHandleNote(statusParam.getHandleNote());
	        }else if(status.equals(2)){
	            //完成退货
	            returnApply.setId(id);
	            returnApply.setStatus(2);
	            returnApply.setReceiveTime(new Date());
	            returnApply.setReceiveMan(statusParam.getReceiveMan());
	            returnApply.setReceiveNote(statusParam.getReceiveNote());
	        }else if(status.equals(3)){
	            //拒绝退货
	            returnApply.setId(id);
	            returnApply.setStatus(3);
	            returnApply.setHandleTime(new Date());
	            returnApply.setHandleMan(statusParam.getHandleMan());
	            returnApply.setHandleNote(statusParam.getHandleNote());
	        }else{
	            return 0;
	        }
	        return orderReturnApplyMapper.updateByPrimaryKeySelective(returnApply);
	    }
	}


