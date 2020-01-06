package com.it2windfly.mymall.service;

import javax.servlet.http.HttpServletRequest;

import com.it2windfly.mymall.dto.OssCallbackResult;
import com.it2windfly.mymall.dto.OssPolicyResult;

public interface OssService {

	OssPolicyResult policy();

	OssCallbackResult callback(HttpServletRequest request);

}
