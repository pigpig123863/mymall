package com.it2windfly.mymall.mbg.mapper;

import com.it2windfly.mymall.mbg.model.SmsCoupon;
import com.it2windfly.mymall.mbg.model.SmsCouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsCouponMapper {
    long countByExample(SmsCouponExample example);

    int deleteByExample(SmsCouponExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsCoupon record);

    int insertSelective(SmsCoupon record);

    List<SmsCoupon> selectByExample(SmsCouponExample example);

    SmsCoupon selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsCoupon record, @Param("example") SmsCouponExample example);

    int updateByExample(@Param("record") SmsCoupon record, @Param("example") SmsCouponExample example);

    int updateByPrimaryKeySelective(SmsCoupon record);

    int updateByPrimaryKey(SmsCoupon record);
}