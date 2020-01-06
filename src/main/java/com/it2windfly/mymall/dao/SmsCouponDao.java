package com.it2windfly.mymall.dao;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.dto.SmsCouponParam;

public interface SmsCouponDao {

	SmsCouponParam getItem(@Param("id")Long id);

}
