package com.eorder.service;

import com.eorder.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目Service接口
 */
public interface CategoryService {

    //通过Id查询类目信息
    ProductCategory findOne(Integer categoryId);
    //查询某商家所有类目
    List<ProductCategory> findAllBySellerId(String sellerId);

    //通过type和sellerId查询
    ProductCategory findBySellerIdAndCategoryType(String sellerId, Integer categoryType);
    //新增和更新类目
    ProductCategory save(ProductCategory productCategory);
    //删除类目
    void delete(ProductCategory productCategory);
}
