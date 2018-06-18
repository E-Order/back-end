package com.eorder.service.impl;

import com.eorder.dataobject.ProductInfo;
import com.eorder.dto.CartDTO;
import com.eorder.enums.ProductStatusEnum;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.repository.ProductInfoRepository;
import com.eorder.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品Service
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductInfoRepository repository;
    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAllBySellerId(String sellerId) {
       return repository.findByProductStatusAndSellerId(ProductStatusEnum.UP.getCode(), sellerId);
    }

    @Override
    public Page<ProductInfo> findAllBySellerId(String sellerId, Pageable pageable) {
        return repository.findBySellerId(sellerId, pageable);
    }

    @Override
    public Page<ProductInfo> findByTypeAndSellerId(Integer categoryType, String sellerId,Pageable pageable) {
        return repository.findByCategoryTypeAndSellerId(categoryType, sellerId,pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            //查询
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId, String sellerId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (!productInfo.getSellerId().equals(sellerId)) {
            throw new SellException(ResultEnum.PRODUCT_OWNER_ERROR);
        }
        if (productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId, String sellerId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (!productInfo.getSellerId().equals(sellerId)) {
            throw new SellException(ResultEnum.PRODUCT_OWNER_ERROR);
        }
        if (productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }

    @Override
    public long countProduct(Integer categoryType, String sellerId) {
        return repository.countProductInfosByAndSellerIdAndCategoryType(sellerId, categoryType);
    }
}
