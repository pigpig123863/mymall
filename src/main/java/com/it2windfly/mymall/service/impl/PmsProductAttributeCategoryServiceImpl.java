package com.it2windfly.mymall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.dao.PmsProductAttributeCategoryDao;
import com.it2windfly.mymall.dto.PmsProductAttributeCategoryItem;
import com.it2windfly.mymall.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.it2windfly.mymall.mbg.model.PmsProductAttributeCategory;
import com.it2windfly.mymall.mbg.model.PmsProductAttributeCategoryExample;
import com.it2windfly.mymall.service.PmsProductAttributeCategoryService;

@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {
	    @Autowired PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
	    @Autowired PmsProductAttributeCategoryDao productAttributeCategoryDao;
	
	    @Override
	    public int create(String name) {
	        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
	        productAttributeCategory.setName(name);
	        return productAttributeCategoryMapper.insertSelective(productAttributeCategory);
	    }

	    @Override
	    public int update(Long id, String name) {
	        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
	        productAttributeCategory.setName(name);
	        productAttributeCategory.setId(id);
	        return productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategory);
	    }

	    @Override
	    public int delete(Long id) {
	        return productAttributeCategoryMapper.deleteByPrimaryKey(id);
	    }

	    @Override
	    public PmsProductAttributeCategory get(Long id) {
	        return productAttributeCategoryMapper.selectByPrimaryKey(id);
	    }

	    @Override
	    public List<PmsProductAttributeCategory> list(Integer pageSize, Integer pageNum) {
	        PageHelper.startPage(pageNum,pageSize);
	        return productAttributeCategoryMapper.selectByExample(new PmsProductAttributeCategoryExample());
	    }

	    @Override
	    public List<PmsProductAttributeCategoryItem> getListWithAttr() {
	        return productAttributeCategoryDao.getListWithAttr();
	    }
	}
