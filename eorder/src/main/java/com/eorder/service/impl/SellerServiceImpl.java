package com.eorder.service.impl;

import com.eorder.dataobject.SellerInfo;
import com.eorder.dto.SellerDTO;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.repository.SellerInfoRepository;
import com.eorder.service.SellerService;
import com.eorder.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerInfoRepository repository;
    @Override
    public SellerInfo create(SellerInfo sellerInfo) {
        SellerInfo temp = repository.findByUsername(sellerInfo.getUsername());
        if (temp != null) {
            throw new SellException(ResultEnum.USERNAME_ALREADY_EXIST);
        }
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        return repository.save(sellerInfo);
    }

    @Override
    public SellerInfo update(SellerInfo sellerInfo, String sellerId) {
        sellerInfo.setSellerId(sellerId);
        return repository.save(sellerInfo);
    }

    @Override
    public SellerInfo findSeller(String username, String password) {
        SellerInfo temp = repository.findByUsername(username);
        if (temp == null) {
            throw new SellException(ResultEnum.SELLER_NOT_EXIST);
        }
        if (!temp.getPassword().equals(password)) {
            throw new SellException(ResultEnum.PASSWORD_ERROR);
        }
        return temp;
    }

    @Override
    public SellerDTO findSeller(String sellerId) {
        SellerInfo temp = repository.findOne(sellerId);
        if (temp == null) {
            throw new SellException(ResultEnum.SELLER_NOT_EXIST);
        }
        SellerDTO sellerVO = new SellerDTO();
        BeanUtils.copyProperties(temp, sellerVO);
        return sellerVO;
    }
}
