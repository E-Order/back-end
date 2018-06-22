package com.eorder.repository;

import com.eorder.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


/**
 * 订单主表
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
    Page<OrderMaster> findByBuyerOpenidAndSellerId(String buyerOpenid, String sellerId, Pageable pageable);
    Page<OrderMaster> findBySellerId(String sellerId, Pageable pageable);
    @Query(value = "select orderMaster from OrderMaster orderMaster where (orderMaster.sellerId = ?1) and (?2 is null or orderMaster.deskId = ?2) and (?3 is null or orderMaster.orderStatus = ?3) and (?4 is null or orderMaster.payStatus = ?4) and (?5 is null or (orderMaster.createTime >= ?5 and orderMaster.createTime < ?6))")
    Page<OrderMaster> findBySellerIdAndDeskIdAndOrderStatusAndPayStatusAndCreateTime(String sellerId, Integer deskId, Integer orderStatus, Integer payStatus, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "select count(orderMaster) from OrderMaster orderMaster where (orderMaster.sellerId = ?1) and (?2 is null or orderMaster.deskId = ?2) and (?3 is null or orderMaster.orderStatus = ?3) and (?4 is null or orderMaster.payStatus = ?4) and (?5 is null or (orderMaster.createTime >= ?5 and orderMaster.createTime < ?6))")
    long countOrderMastersBySellerIdAndDeskIdAndOrderStatusAndPayStatusAndCreateTime(String sellerId, Integer deskId, Integer orderStatus, Integer payStatus, Date startDate, Date endDate);
}
