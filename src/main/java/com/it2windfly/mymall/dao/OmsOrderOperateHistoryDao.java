package com.it2windfly.mymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it2windfly.mymall.mbg.model.OmsOrderOperateHistory;

public interface OmsOrderOperateHistoryDao {

	int insertList(@Param("list")List<OmsOrderOperateHistory> historyList);

}
