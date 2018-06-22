package com.eorder.service;

import com.eorder.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

public interface OrderService {

    /**  创建订单. */
    OrderDTO create(OrderDTO orderDTO, String sellerId);

    /**  查询单个订单. */
    OrderDTO findOne(String orderId);

    /**  查询订单列表. */
    Page<OrderDTO> findListByBuyerOpenid(String buyerOpenid, Pageable pageable);

    /**  查询订单列表. */
    Page<OrderDTO> findListByBuyerOpenidAndSellerId(String buyerOpenid, String sellerId, Pageable pageable);

    Page<OrderDTO> findListBySellerIdAndDeskIdAndOrderStatusAndPayStatusAndDate(String sellerId, Integer deskId, Integer orderStatus, Integer payStatus, Date date, Pageable pageable);

    long countBySellerIdAndDeskIdAndOrderStatusAndPayStatusAndCreateTime(String sellerId, Integer deskId, Integer orderStatus, Integer payStatus,Date date);

    /**  取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);

    /**  完结订单. */
    OrderDTO finish(OrderDTO orderDTO);

    /**  支付订单. */
    OrderDTO paid(OrderDTO orderDTO);

    /**  查询订单列表. */
    Page<OrderDTO> findListBySellerId(String sellerId, Pageable pageable);
}
