package com.eorder.service.impl;

import com.eorder.dataobject.ProductCategory;
import com.eorder.repository.ProductCategoryRepository;
import com.eorder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目Service实现
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAllBySellerId(String sellerId) {
        return repository.findBySellerId(sellerId);
    }

    @Override
    public ProductCategory findBySellerIdAndCategoryType(String sellerId, Integer categoryType) {
        return repository.findBySellerIdAndCategoryType(sellerId,categoryType);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Override
    public void delete(ProductCategory productCategory) {
        repository.delete(productCategory);
    }
}
