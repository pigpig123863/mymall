package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.mbg.model.SmsHomeRecommendSubject;

public interface SmsHomeRecommendSubjectService {

	int create(List<SmsHomeRecommendSubject> recommendSubjectList);

	int updateSort(Long id, Integer sort);

	int delete(List<Long> ids);

	int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

	List<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
