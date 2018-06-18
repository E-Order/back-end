package com.eorder.controller;

import com.eorder.VO.ResultVO;
import com.eorder.VO.ResultVO2;
import com.eorder.dataobject.ProductInfo;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.form.ProductForm;
import com.eorder.service.ProductService;
import com.eorder.utils.KeyUtil;
import com.eorder.utils.ResultVO2Util;
import com.eorder.utils.ResultVOUtil;
import com.eorder.utils.SellerIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 卖家端商品
 */
@RestController
@RequestMapping("seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;
    /**
     * 查询所有商品
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO2 list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size,
                          @RequestParam(value = "categoryType") Integer type,
                          HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        PageRequest pageRequest = new PageRequest(page, size);
        Page<ProductInfo> productInfoPage;
        if (type == null) {
            productInfoPage = productService.findAllBySellerId(sellerId,pageRequest);
        } else {
            productInfoPage = productService.findByTypeAndSellerId(type,sellerId,pageRequest);
        }
        long total = productService.countProduct(type, sellerId);
        return ResultVO2Util.success(total,productInfoPage.getContent());
    }

    /**
     * 商品上架
     * @param productId
     * @return
     */
    @PostMapping("/onSale")
    public ResultVO onSale(@RequestParam("productId") String productId,
                           HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        try {
            productService.onSale(productId, sellerId);
            return ResultVOUtil.success();
        } catch(SellException e) {
            log.error("【卖家端商品上架】 商品上架失败");
            return ResultVOUtil.error(e.getCode(),e.getMessage());
        }
    }

    /**
     * 商品下架
     * @param productId
     * @return
     */
    @PostMapping("/offSale")
    public ResultVO offSale(@RequestParam("productId") String productId,
                            HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        try {
            productService.offSale(productId, sellerId);
            return ResultVOUtil.success();
        } catch(SellException e) {
            log.error("【卖家端商品下架】 商品下架失败");
            return ResultVOUtil.error(e.getCode(),e.getMessage());
        }
    }

    /**
     * 修改商品
     * @param productForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    public ResultVO update(@Valid ProductForm productForm,
                         BindingResult bindingResult,
                           HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        try {
            if (bindingResult.hasErrors()) {
                log.error("【修改商品】参数不正确， ProductForm={}", productForm);
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                        bindingResult.getFieldError().getDefaultMessage());
            }
            ProductInfo productInfo = productService.findOne(productForm.getProductId());
            if (productInfo == null) {
                log.error("【修改商品】商品不存在");
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if (!productInfo.getSellerId().equals(sellerId)) {
                log.error("【修改商品】商品不属于当前用户");
                throw new SellException(ResultEnum.PRODUCT_OWNER_ERROR);
            }
            BeanUtils.copyProperties(productForm, productInfo);
            productInfo.setSellerId(sellerId);
            productService.save(productInfo);
            return ResultVOUtil.success();
        } catch (SellException e) {
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 添加商品
     * @param productForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/add")
    public ResultVO add(@Valid ProductForm productForm,
                        BindingResult bindingResult,
                        HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        try {
            if (bindingResult.hasErrors()) {
                log.error("【添加商品】参数不正确， ProductForm={}", productForm);
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                        bindingResult.getFieldError().getDefaultMessage());
            }
            ProductInfo productInfo = new ProductInfo();
            productForm.setProductId(KeyUtil.getUniqueKey());
            BeanUtils.copyProperties(productForm, productInfo);
            productInfo.setSellerId(sellerId);
            ProductInfo addResult = productService.save(productInfo);
            Map<String, String> map = new HashMap<>();
            map.put("productId", addResult.getProductId());
            return ResultVOUtil.success(map);
        } catch (SellException e) {
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }
    }



}
