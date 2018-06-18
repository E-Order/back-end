package com.eorder.repository;

import com.eorder.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{
    //JpaRepository<ProductCategory,Integer> Integer 代表主键类型

    List<ProductCategory> findBySellerId(String sellerId);
    ProductCategory findBySellerIdAndCategoryType(String sellerId, Integer categoryType);
}
