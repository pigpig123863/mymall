package com.it2windfly.mymall.service;

import java.util.List;

import com.it2windfly.mymall.dto.PmsBrandParam;
import com.it2windfly.mymall.mbg.model.PmsBrand;

public interface PmsBrandService {

	int createBrand(PmsBrandParam brandParam);

	List<PmsBrand> list(int pageNum, int pageSize);

	int updateBrand(Long id, PmsBrandParam pmsBrandParam);

	int deleteBrand(Long id);

	List<PmsBrand> list(String keyword, Integer pageNum, Integer pageSize);

	PmsBrand getBrand(Long id);

	int deleteBrand(List<Long> ids);

	int updateShowStatus(List<Long> ids, Integer showStatus);

	int updateFactoryStatus(List<Long> ids, Integer factoryStatus);

}
