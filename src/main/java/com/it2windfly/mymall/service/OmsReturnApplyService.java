package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.OmsOrderReturnApplyResult;
import com.it2windfly.mymall.dto.OmsReturnApplyQueryParam;
import com.it2windfly.mymall.dto.OmsUpdateStatusParam;
import com.it2windfly.mymall.mbg.model.OmsOrderReturnApply;

public interface OmsReturnApplyService {

	List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum);

	int delete(List<Long> ids);

	OmsOrderReturnApplyResult getItem(Long id);

	int updateStatus(Long id, OmsUpdateStatusParam statusParam);

}
