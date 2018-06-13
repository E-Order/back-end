package com.project.eorder.service;

import com.project.eorder.dataobject.ProductInfo;
//import com.eorder.dto.CartDTO;
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
    List<ProductInfo> findUpAll();


    /**
     * 所有商品 分页
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);


    //加库存

   // void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    //void decreaseStock(List<CartDTO> cartDTOList);

}
