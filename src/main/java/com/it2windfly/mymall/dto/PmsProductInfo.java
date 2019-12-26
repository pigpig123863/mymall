package com.it2windfly.mymall.dto;


import com.it2windfly.mymall.dto.PmsProductParam;

/**
 * 查询单个产品进行修改时返回的结果
 */
public class PmsProductInfo extends PmsProductParam {
    //商品所选分类的父id
    private Long cateParentId;

    public Long getCateParentId() {
        return cateParentId;
    }

    public void setCateParentId(Long cateParentId) {
        this.cateParentId = cateParentId;
    }
}
