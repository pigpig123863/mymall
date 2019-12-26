package com.it2windfly.mymall.service.impl;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.it2windfly.mymall.dao.*;
import com.it2windfly.mymall.dto.PmsProductInfo;
import com.it2windfly.mymall.dto.PmsProductParam;
import com.it2windfly.mymall.dto.PmsProductQueryParam;
import com.it2windfly.mymall.mbg.mapper.*;
import com.it2windfly.mymall.mbg.model.*;
import com.it2windfly.mymall.mbg.model.PmsProductExample.Criteria;
import com.it2windfly.mymall.service.PmsProductService;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

public class PmsProductServiceImpl implements PmsProductService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpl.class);
	@Autowired PmsProductMapper pmsProductMapper;
	@Autowired MemberPriceDao memberPriceDao;
	@Autowired ProductLadderDao productLadderDao;
	@Autowired ProductFullReductionDao productFullReductionDao;
	@Autowired SkuStockDao skuStockDao;
	@Autowired ProductAttributeValueDao productAttributeValueDao;
	@Autowired SubjectProductRelationDao subjectProductRelationDao;
	@Autowired PrefrenceAreaProductRelationDao prefrenceAreaProductRelationDao;
	@Autowired PmsProductDao pmsProductDao;
	@Autowired PmsMemberPriceMapper pmsMemberPriceMapper;
	@Autowired PmsProductLadderMapper productLadderMapper;
	@Autowired PmsProductFullReductionMapper productFullReductionMapper;
	@Autowired PmsSkuStockMapper skuStockMapper;
	@Autowired PmsProductAttributeValueMapper productAttributeValueMapper;
	@Autowired CmsSubjectProductRelationMapper subjectProductRelationMapper;
	@Autowired CmsPrefrenceAreaProductRelationMapper prefrenceAreaProductRelationMapper;
	
	@Override
	public int create(PmsProductParam pmsProductParam) {
		int count;
		PmsProduct product = pmsProductParam;
		product.setId(null);
		pmsProductMapper.insertSelective(product);
		Long productId = product.getId();
		//会员价格
		relateAndInsertList(memberPriceDao,pmsProductParam.getMemberPriceList(),productId);
		//阶梯价格
        relateAndInsertList(productLadderDao, pmsProductParam.getProductLadderList(), productId);
        //满减价格
        relateAndInsertList(productFullReductionDao, pmsProductParam.getProductFullReductionList(), productId);
        //处理sku的编码
        handleSkuStockCode(pmsProductParam.getSkuStockList(),productId);
        //添加sku库存信息
        relateAndInsertList(skuStockDao, pmsProductParam.getSkuStockList(), productId);
        //添加商品参数,添加自定义商品规格
        relateAndInsertList(productAttributeValueDao, pmsProductParam.getProductAttributeValueList(), productId);
        //关联专题
        relateAndInsertList(subjectProductRelationDao, pmsProductParam.getSubjectProductRelationList(), productId);
        //关联优选
        relateAndInsertList(prefrenceAreaProductRelationDao, pmsProductParam.getPrefrenceAreaProductRelationList(), productId);
        count = 1;
        return count;
	}

	private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
		if(skuStockList==null)return;
		for(int i=0;i<skuStockList.size();i++){
			PmsSkuStock skuStock = skuStockList.get(i);
			if(StrUtil.isEmpty(skuStock.getSkuCode())){
				SimpleDateFormat sdf = new SimpleDateFormat();
				StringBuilder sb = new StringBuilder();
				//日期
				sb.append(sdf.format(new Date()));
				//四位商品id
				sb.append(String.format("%04d", productId));
				//三位索引id
				sb.append(String.format("%03d", i+1));
				skuStock.setSkuCode(sb.toString());
			}
		}
		
		
	}

	private void relateAndInsertList(Object dao, List dataList,Long productId) {
		if(CollUtil.isEmpty(dataList))return;
			try {
				for(Object item:dataList){
					Method setId = item.getClass().getMethod("setId", Long.class);
					setId.invoke(item, (Long)null);
					Method setProductId = item.getClass().getMethod("setProductId", Long.class);
					setProductId.invoke(item, productId);
				} 
				Method insertList = dao.getClass().getMethod("insertList", List.class);
				insertList.invoke(dao, dataList);
			}
			catch (Exception e) {
				LOGGER.warn("创建产品出错：{}",e.getMessage());
				throw new RuntimeException(e.getMessage());
			} 
		}

	@Override
	public PmsProductInfo getUpdateInfo(Long id) {
		return pmsProductDao.getUpdateInfo(id);
	}

	@Override
	public int update(Long id, PmsProductParam pmsProductParam) {
		int count;
		PmsProduct product = pmsProductParam;
		product.setId(id);
		pmsProductMapper.updateByPrimaryKeySelective(product);
		//会员价格
		PmsMemberPriceExample example = new PmsMemberPriceExample();
		example.createCriteria().andProductIdEqualTo(id);
		pmsMemberPriceMapper.deleteByExample(example);
		relateAndInsertList(memberPriceDao, pmsProductParam.getMemberPriceList(), id);
		//阶梯价格
        PmsProductLadderExample ladderExample = new PmsProductLadderExample();
        ladderExample.createCriteria().andProductIdEqualTo(id);
        productLadderMapper.deleteByExample(ladderExample);
        relateAndInsertList(productLadderDao, pmsProductParam.getProductLadderList(), id);
        //满减价格
        PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
        fullReductionExample.createCriteria().andProductIdEqualTo(id);
        productFullReductionMapper.deleteByExample(fullReductionExample);
        relateAndInsertList(productFullReductionDao, pmsProductParam.getProductFullReductionList(), id);
        //修改sku库存信息
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(id);
        skuStockMapper.deleteByExample(skuStockExample);
        handleSkuStockCode(pmsProductParam.getSkuStockList(),id);
        relateAndInsertList(skuStockDao, pmsProductParam.getSkuStockList(), id);
        //修改商品参数,添加自定义商品规格
        PmsProductAttributeValueExample productAttributeValueExample = new PmsProductAttributeValueExample();
        productAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        productAttributeValueMapper.deleteByExample(productAttributeValueExample);
        relateAndInsertList(productAttributeValueDao, pmsProductParam.getProductAttributeValueList(), id);
        //关联专题
        CmsSubjectProductRelationExample subjectProductRelationExample = new CmsSubjectProductRelationExample();
        subjectProductRelationExample.createCriteria().andProductIdEqualTo(id);
        subjectProductRelationMapper.deleteByExample(subjectProductRelationExample);
        relateAndInsertList(subjectProductRelationDao, pmsProductParam.getSubjectProductRelationList(), id);
        //关联优选
        CmsPrefrenceAreaProductRelationExample prefrenceAreaExample = new CmsPrefrenceAreaProductRelationExample();
        prefrenceAreaExample.createCriteria().andProductIdEqualTo(id);
        prefrenceAreaProductRelationMapper.deleteByExample(prefrenceAreaExample);
        relateAndInsertList(prefrenceAreaProductRelationDao, pmsProductParam.getPrefrenceAreaProductRelationList(), id);
        count = 1;
        return count;
	}

	@Override
	public List<PmsProduct> list(PmsProductQueryParam pmsProductQueryParam, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PmsProductExample example = new PmsProductExample();
		Criteria criteria = example.createCriteria();
		criteria.andDeleteStatusEqualTo(0);
		if(pmsProductQueryParam.getPublishStatus()!=null){
			criteria.andPublishStatusEqualTo(pmsProductQueryParam.getPublishStatus());
		}
		if(pmsProductQueryParam.getVerifyStatus()!=null){
			criteria.andVerifyStatusEqualTo(pmsProductQueryParam.getVerifyStatus());
		}
		if (!StringUtils.isEmpty(pmsProductQueryParam.getKeyword())) {
            criteria.andNameLike("%" + pmsProductQueryParam.getKeyword() + "%");
        }
        if (!StringUtils.isEmpty(pmsProductQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(pmsProductQueryParam.getProductSn());
        }
        if (pmsProductQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(pmsProductQueryParam.getBrandId());
        }
        if (pmsProductQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(pmsProductQueryParam.getProductCategoryId());
        }
        return pmsProductMapper.selectByExample(example);
	}


			
		
	}

