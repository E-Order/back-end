package com.eorder.controller;

import com.eorder.VO.ResultVO;
import com.eorder.dataobject.ProductInfo;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.form.ProductForm;
import com.eorder.service.ProductService;
import com.eorder.utils.KeyUtil;
import com.eorder.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
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
    public ResultVO list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest request = new PageRequest(page, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        return ResultVOUtil.success(productInfoPage.getContent());
    }

    /**
     * 商品上架
     * @param productId
     * @return
     */
    @PostMapping("/onSale")
    public ResultVO onSale(@RequestParam("productId") String productId) {
        try {
            productService.onSale(productId);
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
    public ResultVO offSale(@RequestParam("productId") String productId) {
        try {
            productService.offSale(productId);
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
                         BindingResult bindingResult) {
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
            BeanUtils.copyProperties(productForm, productInfo);
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
                        BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                log.error("【添加商品】参数不正确， ProductForm={}", productForm);
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                        bindingResult.getFieldError().getDefaultMessage());
            }
            ProductInfo productInfo = new ProductInfo();
            productForm.setProductId(KeyUtil.getUniqueKey());
            BeanUtils.copyProperties(productForm, productInfo);
            ProductInfo addResult = productService.save(productInfo);

            Map<String, String> map = new HashMap<>();
            map.put("productId", addResult.getProductId());
            return ResultVOUtil.success(map);
        } catch (SellException e) {
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }

    }



}
