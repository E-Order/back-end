package com.eorder.service.impl;

import com.eorder.dataobject.ProductInfo;
import com.eorder.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo result = productService.findOne("123456");
        Assert.assertEquals("123456", result.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAllBySellerId("123");
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() {
        //page 第几页 size 每页有多少个
        PageRequest request = new PageRequest(1,1);
        Page<ProductInfo> productInfoPage = productService.findAllBySellerId("123",request);
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Test
    public void fingByType() {
        PageRequest request = new PageRequest(0,10);
        Page<ProductInfo> productInfoPage = productService.findByTypeAndSellerId(2,"123",request);
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(3);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSale() {
        ProductInfo result = productService.onSale("123456","123");
        Assert.assertEquals(ProductStatusEnum.UP.getCode(),result.getProductStatus());
    }

    @Test
    public void offSale() {
        ProductInfo result = productService.offSale("123456","123");
        Assert.assertEquals(ProductStatusEnum.DOWN.getCode(),result.getProductStatus());
    }
}