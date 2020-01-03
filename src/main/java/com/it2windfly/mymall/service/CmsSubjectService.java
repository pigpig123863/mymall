package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.CmsSubject;

public interface CmsSubjectService {

	List<CmsSubject> listAll();

	List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize);

}
