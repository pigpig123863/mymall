package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it2windfly.mymall.dao.PmsSkuStockDao;
import com.it2windfly.mymall.mbg.mapper.PmsSkuStockMapper;
import com.it2windfly.mymall.mbg.model.PmsSkuStock;
import com.it2windfly.mymall.mbg.model.PmsSkuStockExample;
import com.it2windfly.mymall.mbg.model.PmsSkuStockExample.Criteria;
import com.it2windfly.mymall.service.PmsSkuStockService;

import cn.hutool.core.util.StrUtil;

@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {
	@Autowired PmsSkuStockMapper pmsSkuStockMapper;
	@Autowired PmsSkuStockDao skuStockDao;

	@Override
	public List<PmsSkuStock> getList(Long pid, String keyword) {
		PmsSkuStockExample example = new PmsSkuStockExample();
		PmsSkuStockExample.Criteria criteria = example.createCriteria().andProductIdEqualTo(pid);
		if(StrUtil.isEmpty(keyword)){
			criteria.andSkuCodeLike("%"+keyword+"%");
		}
		return pmsSkuStockMapper.selectByExample(example);
	}

	@Override
	public int update(Long pid, List<PmsSkuStock> skuStockList){
		return skuStockDao.replaceList(skuStockList);
	}
	
}
