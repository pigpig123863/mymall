package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.mbg.model.PmsSkuStock;

public interface PmsSkuStockDao {
	
	int insertList(@Param("list")List<PmsSkuStock> skuStockList);
	
	int replaceList(@Param("list")List<PmsSkuStock> skuStockList);

}
