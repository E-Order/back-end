package com.eorder.dto;

import com.eorder.dataobject.OrderDetail;
import com.eorder.enums.OrderStatusEnum;
import com.eorder.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
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
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
    List<OrderDetail> orderDetailList;
}
