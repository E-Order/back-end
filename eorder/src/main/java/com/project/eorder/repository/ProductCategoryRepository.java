package com.project.eorder.repository;

import com.project.eorder.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{
    //JpaRepository<ProductCategory,Integer> Integer 代表主键类型
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
