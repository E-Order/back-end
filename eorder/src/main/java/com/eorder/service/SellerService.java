package com.eorder.service;

import com.eorder.dataobject.SellerInfo;
import com.eorder.dto.SellerDTO;

/**
 * 卖家
 */
public interface SellerService {
    /**
     * 增加卖家
     * @param sellerInfo
     * @return
     */
    SellerInfo create(SellerInfo sellerInfo);

    /**
     * 修改商家信息
     */
    SellerInfo update(SellerInfo sellerInfo, String sellerId);
    /**
     * 查询卖家
     * @param username
     * @param password
     * @return
     */
    SellerInfo findSeller(String username, String password);
    SellerDTO findSeller(String sellerId);
}
