package com.eorder.converter;

import com.eorder.dataobject.OrderDetail;
import com.eorder.dto.OrderDTO;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setDeskId(new Integer(orderForm.getDeskId()));
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setOrderAmount(new BigDecimal(orderForm.getAmount()));
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
                orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e) {
            log.error("【对象转换】 错误，string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }


        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;

    }
}
