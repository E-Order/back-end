package com.project.eorder.service;

import com.project.eorder.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目Service接口
 */
public interface CategoryService {

    //通过Id查询类目信息
    ProductCategory findOne(Integer categoryId);
    //查询所有类目
    List<ProductCategory> findAll();
    // 通过type类型查询
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    //新增和更新类目
    ProductCategory save(ProductCategory productCategory);

}
