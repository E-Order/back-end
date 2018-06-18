package com.eorder.service.impl;

import com.eorder.dataobject.SellerInfo;
import com.eorder.service.SellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {
    @Autowired
    SellerService sellerService;
    @Test
    public void create() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setUsername("Wang");
        sellerInfo.setPassword("123456");
        SellerInfo result = sellerService.create(sellerInfo);
        Assert.assertEquals("Wang", result.getUsername());
    }

    @Test
    public void update() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId("1529155813731648165");
        sellerInfo.setUsername("Tang");
        sellerInfo.setPassword("123456");
        SellerInfo result = sellerService.update(sellerInfo,"123");
        Assert.assertEquals("Tang", result.getUsername());
    }

    @Test
    public void findSeller() {
        SellerInfo result = sellerService.findSeller("Tang","123456");
        Assert.assertEquals("Tang", result.getUsername());
    }
}