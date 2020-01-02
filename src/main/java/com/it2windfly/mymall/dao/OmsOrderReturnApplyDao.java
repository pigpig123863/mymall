package com.it2windfly.mymall.dao;

import java.util.List;

import com.it2windfly.mymall.dto.OmsOrderReturnApplyResult;
import com.it2windfly.mymall.dto.OmsReturnApplyQueryParam;
import com.it2windfly.mymall.mbg.model.OmsOrderReturnApply;

public interface OmsOrderReturnApplyDao {

	List<OmsOrderReturnApply> getList(OmsReturnApplyQueryParam queryParam);

	OmsOrderReturnApplyResult getDetail(Long id);

}
