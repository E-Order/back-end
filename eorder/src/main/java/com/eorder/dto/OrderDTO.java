package com.eorder.dto;

import com.eorder.dataobject.OrderDetail;
import com.eorder.enums.OrderStatusEnum;
import com.eorder.enums.PayStatusEnum;
import com.eorder.utils.EnumUtil;
import com.eorder.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 桌号. */
    private Integer deskId;

    /** 买家微信id. */
    private String buyerOpenid;

    /** 订单总金额.*/
    private BigDecimal orderAmount;

    /** 订单状态默认为0为新下单. */
    private Integer orderStatus = OrderStatusEnum.New.getCode();

    /** 支付状态默认为0未支付. */
    private Integer payStatus = PayStatusEnum.Wait.getCode();

    /** 创建时间,需要时间来进行排序. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    /**
     * List<OrderDetail orderDetailList = new ArrayList<>();
     * 设置初始值，就不会返回null
     */

    List<OrderDetail> orderDetailList;

    /*@JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }*/
}
