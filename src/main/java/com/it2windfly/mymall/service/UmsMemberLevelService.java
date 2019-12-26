package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.UmsMemberLevel;

public interface UmsMemberLevelService {

	List<UmsMemberLevel> list(int defaultStatus);

}
