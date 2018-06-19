package com.eorder.repository;

import com.eorder.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.TransactionScoped;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMaterRepositoryTest {

    @Autowired
    private OrderMaterRepository repository;

    private final String OPENID = "456";
    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setDeskId(1234);
        orderMaster.setOrderAmount(new BigDecimal(2.33));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);



    }
    @Test
    public void findByBuyerOpenid() throws  Exception{
        PageRequest request = new PageRequest(0, 3);
       Page<OrderMaster> result =  repository.findByBuyerOpenid(OPENID, request);
       Assert.assertNotEquals(0, result.getTotalElements());
       System.out.println(((Page) result).getTotalElements());


    }
}