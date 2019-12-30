package com.it2windfly.mymall.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.dao.PmsProductAttributeDao;
import com.it2windfly.mymall.dto.PmsProductAttributeParam;
import com.it2windfly.mymall.dto.ProductAttrInfo;
import com.it2windfly.mymall.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.it2windfly.mymall.mbg.mapper.PmsProductAttributeMapper;
import com.it2windfly.mymall.mbg.model.*;
import com.it2windfly.mymall.service.PmsProductAttributeService;

import java.util.List;

@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService{
	@Autowired PmsProductAttributeMapper productAttributeMapper;
	@Autowired PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
	@Autowired PmsProductAttributeDao productAttributeDao;

	@Override
	public List<PmsProductAttribute> List(Long cid, Integer type, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		PmsProductAttributeExample example = new PmsProductAttributeExample();
		example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
		return productAttributeMapper.selectByExample(example);
		
	}

	@Override
	public int create(PmsProductAttributeParam productAttributeParam) {
		PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(productAttributeParam, pmsProductAttribute);
        int count = productAttributeMapper.insertSelective(pmsProductAttribute);
        //新增商品属性以后需要更新商品属性分类数量
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        if(pmsProductAttribute.getType()==0){
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount()+1);
        }else if(pmsProductAttribute.getType()==1){
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount()+1);
        }
        productAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
        return count;
    }

	@Override
	public int update(Long id, PmsProductAttributeParam productAttributeParam) {
		PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        pmsProductAttribute.setId(id);
        BeanUtils.copyProperties(productAttributeParam, pmsProductAttribute);
        return productAttributeMapper.updateByPrimaryKeySelective(pmsProductAttribute);
	}

	@Override
	public PmsProductAttribute get(Long id) {
		return productAttributeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int delete(List<Long> ids) {
		//获取分类
        PmsProductAttribute pmsProductAttribute = productAttributeMapper.selectByPrimaryKey(ids.get(0));
        Integer type = pmsProductAttribute.getType();
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids);
        int count = productAttributeMapper.deleteByExample(example);
        //删除完成后修改数量
        if(type==0){
            if(pmsProductAttributeCategory.getAttributeCount()>=count){
                pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount()-count);
            }else{
                pmsProductAttributeCategory.setAttributeCount(0);
            }
        }else if(type==1){
            if(pmsProductAttributeCategory.getParamCount()>=count){
                pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount()-count);
            }else{
                pmsProductAttributeCategory.setParamCount(0);
            }
        }
        productAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
        return count;
    }

	@Override
	public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
		return productAttributeDao.getProductAttrInfo(productCategoryId);
	}
	
	
}

