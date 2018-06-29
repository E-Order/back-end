package com.eorder.dto;

import com.eorder.dataobject.OrderDetail;
import com.eorder.enums.OrderStatusEnum;
import com.eorder.enums.PayStatusEnum;
import com.eorder.VO.OrderDetailVO;
import com.eorder.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDetailDTO {

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

    List<OrderDetailVO> orderDetailVOList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getDeskId() {
        return deskId;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

    public String getBuyerOpenid() {
        return buyerOpenid;
    }

    public void setBuyerOpenid(String buyerOpenid) {
        this.buyerOpenid = buyerOpenid;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderDetailVO> getOrderDetailVOList() {
        return orderDetailVOList;
    }

    public void setOrderDetailVOList(List<OrderDetailVO> orderDetailVOList) {
        this.orderDetailVOList = orderDetailVOList;
    }
}
