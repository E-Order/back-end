package com.eorder.repository;

import com.eorder.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * 商品信息
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String>{

    /**根据商品状态和sellerId查询，已上架和未上架*/
    List<ProductInfo> findByProductStatusAndSellerId(Integer productStatus, String sellerId);
    /**根据商品类目和sellerId查询. */
    Page<ProductInfo> findByCategoryTypeAndSellerId(Integer categoryType, String sellerId,Pageable pageable);
    Page<ProductInfo> findBySellerId(String sellerId, Pageable pageable);
    @Query(value = "select count(productInfo) from ProductInfo productInfo where (productInfo.sellerId = ?1) and (?2 is null or productInfo.categoryType = ?2)")
    long countProductInfosByAndSellerIdAndCategoryType(String sellerId,Integer categoryType);
}
