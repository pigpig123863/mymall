package com.it2windfly.mymall.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.dao.PmsProductCategoryDao;
import com.it2windfly.mymall.dao.PmsproductCategoryAttributeRelationDao;
import com.it2windfly.mymall.dto.PmsProductCategoryParam;
import com.it2windfly.mymall.dto.PmsProductCategoryWithChildrenItem;
import com.it2windfly.mymall.mbg.mapper.*;
import com.it2windfly.mymall.mbg.model.*;
import com.it2windfly.mymall.service.PmsProductCategoryService;

import cn.hutool.core.collection.CollUtil;

@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {
	@Autowired PmsproductCategoryAttributeRelationDao productCategoryAttributeRelationDao;
	@Autowired PmsProductMapper productMapper;
	@Autowired PmsProductCategoryAttributeRelationMapper productCategoryAttributeRelationMapper;
	@Autowired PmsProductCategoryMapper productCategoryMapper;
	@Autowired PmsProductCategoryDao productCategoryDao;
	
	@Override
	public int create(PmsProductCategoryParam productCategoryParam) {
		PmsProductCategory category = new PmsProductCategory();
		category.setProductCount(0);
		BeanUtils.copyProperties(productCategoryParam, category);
		 //没有父分类时为一级分类
		setCategoryLevel(category);
		int count = productCategoryMapper.insertSelective(category);
		 //创建筛选属性关联
		List<Long> productAttributeIdList = productCategoryParam.getProductAttributeIdList();
		if(!CollectionUtils.isEmpty(productAttributeIdList)){
            insertRelationList(category.getId(), productAttributeIdList);
		}
		return count;
	}

	private void setCategoryLevel(PmsProductCategory category) {
		if(category.getParentId()==null){
			category.setLevel(0);
		}else{
			PmsProductCategory parentCategory = productCategoryMapper.selectByPrimaryKey(category.getParentId());
			if(parentCategory!=null){
				category.setLevel(parentCategory.getLevel()+1);
			}else{
				category.setLevel(0);
			}
		}
		
	}

	private void insertRelationList(Long id, List<Long> productAttributeIdList) {
		List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
		for(Long paid:productAttributeIdList){
			PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
			relation.setProductAttributeId(paid);
			relation.setProductCategoryId(id);
			relationList.add(relation);
		}
		productCategoryAttributeRelationDao.insertList(relationList);
		
	}

	@Override
	public int update(Long id, PmsProductCategoryParam productCategoryParam) {
		//重新新建一个分类
		PmsProductCategory category = new PmsProductCategory();
		category.setId(id);
		BeanUtils.copyProperties(productCategoryParam, category);
		setCategoryLevel(category);
		//更新商品中的分类名
		PmsProduct product = new PmsProduct();
		product.setProductCategoryName(category.getName());
		PmsProductExample example = new PmsProductExample();
		example.createCriteria().andProductCategoryIdEqualTo(id);
		productMapper.updateByExampleSelective(product, example);
		//更新商品分类属性
		if(!CollUtil.isEmpty(productCategoryParam.getProductAttributeIdList())){
			PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
			relationExample.createCriteria().andProductCategoryIdEqualTo(id);
			productCategoryAttributeRelationMapper.deleteByExample(relationExample);
			insertRelationList(id,productCategoryParam.getProductAttributeIdList());
		}else{
			PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
			relationExample.createCriteria().andProductCategoryIdEqualTo(id);
			productCategoryAttributeRelationMapper.deleteByExample(relationExample);
		}
		return productCategoryMapper.updateByPrimaryKeySelective(category);
	}

	@Override
	public List<PmsProductCategory> list(Long parentId, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		PmsProductCategoryExample example = new PmsProductCategoryExample();
		example.setOrderByClause("sort desc");
		example.createCriteria().andParentIdEqualTo(parentId);
		return productCategoryMapper.selectByExample(example);
	
	}

	@Override
	public PmsProductCategory get(Long id) {
		return productCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int delete(Long id) {
		return productCategoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateNavStatus(List<Long> ids, Integer navStatus) {
		PmsProductCategory category = new PmsProductCategory();
		category.setNavStatus(navStatus);
		PmsProductCategoryExample example = new PmsProductCategoryExample();	
		example.createCriteria().andIdIn(ids);
		return productCategoryMapper.updateByExampleSelective(category, example);
	}

	@Override
	public int updateShowStatus(List<Long> ids, Integer showStatus) {
		PmsProductCategory category = new PmsProductCategory();
		category.setNavStatus(showStatus);
		PmsProductCategoryExample example = new PmsProductCategoryExample();	
		example.createCriteria().andIdIn(ids);
		return productCategoryMapper.updateByExampleSelective(category, example);
	}

	@Override
	public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
		return productCategoryDao.listWithChildren();
	}
	
	

}
