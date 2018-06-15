package com.eorder.form;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * 前端创建订单传入表单格式
 */
@Data
public class OrderForm {

    /**
     * 桌号
     */
    @NotEmpty(message = "桌号必填")
    private String deskId;

    /**
     * 买家微信openid
     */
    @NotEmpty(message = "openid必填")
    private String openid;


    /**
     * 订单总价
     */
    @NotEmpty(message = "总价必填")
    private String amount;


    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
