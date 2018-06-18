package com.eorder.repository;

import com.eorder.dataobject.ProductCategory;
import com.eorder.repository.ProductCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest() {
        //ProductCategory productCategory = new ProductCategory("女生最爱",3);
        //ProductCategory result = repository.save(productCategory);
        //断言
        //Assert.assertNotNull(result);
        //更新
        //productCategory.setCategoryId(2);
        //productCategory.setCategoryName("男生最爱");
        //productCategory.setCategoryType(3);
        //repository.save(productCategory);
        ProductCategory productCategory;
        productCategory = repository.findOne(1);
        productCategory.setCategoryType(20);
        repository.save(productCategory);
    }


}