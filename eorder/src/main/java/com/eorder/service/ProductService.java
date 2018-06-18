package com.eorder.service;

import com.eorder.dataobject.ProductInfo;
import com.eorder.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 */
public interface ProductService {

    ProductInfo findOne(String productId);
    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAllBySellerId(String sellerId);


    /**
     * 所有商品 分页
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAllBySellerId(String sellerId, Pageable pageable);

    Page<ProductInfo> findByTypeAndSellerId(Integer categoryType, String sellerId,Pageable pageable);
    ProductInfo save(ProductInfo productInfo);


    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);


    //上架
    ProductInfo onSale(String productId, String sellerId);
    //下架
    ProductInfo offSale(String productId, String sellerId);

    long countProduct(Integer categoryType, String sellerId);
}
