package com.eorder.controller;

import com.eorder.VO.ProductInfoVO;
import com.eorder.VO.ProductVO;
import com.eorder.VO.ResultVO;
import com.eorder.dataobject.ProductCategory;
import com.eorder.dataobject.ProductInfo;
import com.eorder.exception.SellException;
import com.eorder.service.CategoryService;
import com.eorder.service.ProductService;
import com.eorder.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 买家端商品
 */
@RestController
@RequestMapping("/buyer/product")

public class BuyerProductController {
    //查询商品
    @Autowired
    private ProductService productService;
    //查询类目
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(@RequestParam("sellerId") String sellerId) {
        try {
            //1.查询所有上架商品
            List<ProductInfo> productInfoList = productService.findUpAllBySellerId(sellerId);
            List<ProductCategory> productCategoryList = categoryService.findAllBySellerId(sellerId);
            //3.数据拼装
            List<ProductVO> productVOList = new ArrayList<>();
            for (ProductCategory productCategory: productCategoryList) {
                //类目
                ProductVO productVO = new ProductVO();
                productVO.setCategoryType(productCategory.getCategoryType());
                productVO.setCategoryName(productCategory.getCategoryName());

                List<ProductInfoVO> productInfoVOList = new ArrayList<>();
                //类目下的商品
                for (ProductInfo productInfo:productInfoList) {
                    if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                        ProductInfoVO productInfoVO = new ProductInfoVO();
                        //属性的复制
                        BeanUtils.copyProperties(productInfo, productInfoVO);
                        productInfoVOList.add(productInfoVO);
                    }
                }

                productVO.setProductInfoVOList(productInfoVOList);
                productVOList.add(productVO);
            }
            return ResultVOUtil.success(productVOList);
        } catch (SellException e) {
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }

    }
}
