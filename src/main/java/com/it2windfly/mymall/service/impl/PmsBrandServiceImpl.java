package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.dto.PmsBrandParam;
import com.it2windfly.mymall.mbg.mapper.*;
import com.it2windfly.mymall.mbg.model.*;
import com.it2windfly.mymall.service.PmsBrandService;

import cn.hutool.core.util.StrUtil;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {
	@Autowired PmsBrandMapper pmsBrandMapper;
	@Autowired PmsProductMapper productMapper;

	public int createBrand(PmsBrandParam brandParam) {
		PmsBrand brand = new PmsBrand();
		BeanUtils.copyProperties(brandParam, brand);
		if(StrUtil.isEmpty(brand.getFirstLetter())){
			brand.setFirstLetter(brand.getName().substring(0, 1));
		}
		return pmsBrandMapper.insertSelective(brand);
	}


	@Override
	public List<PmsBrand> list(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return pmsBrandMapper.selectByExample(new PmsBrandExample());
	}


	@Override
	public int updateBrand(Long id, PmsBrandParam pmsBrandParam) {
		PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam, pmsBrand);
        pmsBrand.setId(id);
        //如果创建时首字母为空，取名称的第一个为首字母
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        //更新品牌时要更新商品中的品牌名称
        PmsProduct product = new PmsProduct();
        product.setBrandName(pmsBrand.getName());
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andBrandIdEqualTo(id);
        productMapper.updateByExampleSelective(product,example);
        return pmsBrandMapper.updateByPrimaryKeySelective(pmsBrand);
	}


	@Override
	public int deleteBrand(Long id) {
		 return pmsBrandMapper.deleteByPrimaryKey(id);
	}


	@Override
	public List<PmsBrand> list(String keyword, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria = pmsBrandExample.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return pmsBrandMapper.selectByExample(pmsBrandExample);
	}


	@Override
	public PmsBrand getBrand(Long id) {
		 return pmsBrandMapper.selectByPrimaryKey(id);
	}


	@Override
	public int deleteBrand(List<Long> ids) {
		PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return pmsBrandMapper.deleteByExample(pmsBrandExample);
	}


	@Override
	public int updateShowStatus(List<Long> ids, Integer showStatus) {
		PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setShowStatus(showStatus);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return pmsBrandMapper.updateByExampleSelective(pmsBrand, pmsBrandExample);
	}


	@Override
	public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
		PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setFactoryStatus(factoryStatus);
        PmsBrandExample pmsBrandExample = new PmsBrandExample();
        pmsBrandExample.createCriteria().andIdIn(ids);
        return pmsBrandMapper.updateByExampleSelective(pmsBrand, pmsBrandExample);
	}

}
