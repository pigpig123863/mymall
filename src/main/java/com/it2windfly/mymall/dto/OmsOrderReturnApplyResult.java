package com.it2windfly.mymall.dto;

import com.it2windfly.mymall.mbg.model.OmsCompanyAddress;
import com.it2windfly.mymall.mbg.model.OmsOrderReturnApply;

import lombok.Getter;
import lombok.Setter;

/**
 * 申请信息封装
 */
public class OmsOrderReturnApplyResult extends OmsOrderReturnApply {
    @Getter
    @Setter
    private OmsCompanyAddress companyAddress;
}

