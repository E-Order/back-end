package com.eorder.repository;

import com.eorder.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单主表
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
    Page<OrderMaster> findByBuyerOpenidAndSellerId(String buyerOpenid, String sellerId, Pageable pageable);
    Page<OrderMaster> findBySellerId(String sellerId, Pageable pageable);
}
