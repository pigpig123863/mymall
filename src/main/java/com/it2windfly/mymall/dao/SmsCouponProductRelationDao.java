package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.it2windfly.mymall.mbg.model.SmsCouponProductRelation;

public interface SmsCouponProductRelationDao {

	int insertList(@Param("list")List<SmsCouponProductRelation> list);

}
