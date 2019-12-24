package com.it2windfly.mymall.service;

import org.springframework.stereotype.Service;

import com.it2windfly.mymall.common.api.CommonResult;

@Service
public interface UmsMemberService {

	CommonResult generateAuthCode(String telephone);

	CommonResult verifyAuthCode(String telephone, String authCode);

}
