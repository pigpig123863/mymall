package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.dto.PmsProductInfo;
import com.it2windfly.mymall.mbg.model.PmsMemberPrice;


public interface PmsProductDao {

	PmsProductInfo getUpdateInfo(Long id);
	int insertList(@Param("list") List<PmsMemberPrice> memberPriceList);

}
