package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.PmsSkuStock;

public interface PmsSkuStockService {

	List<PmsSkuStock> getList(Long pid, String keyword);

	int update(Long pid, List<PmsSkuStock> skuStockList);

}
