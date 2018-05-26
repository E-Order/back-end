package com.eorder.repository;

import com.eorder.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品信息
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String>{

    /**根据商品状态查询，已上架和未上架*/
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
