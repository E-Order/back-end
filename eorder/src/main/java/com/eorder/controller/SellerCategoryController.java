package com.eorder.controller;

import com.eorder.VO.ResultVO;
import com.eorder.aspect.SellerAuthorizeAspect;
import com.eorder.dataobject.ProductCategory;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.form.CategoryForm;
import com.eorder.service.CategoryService;
import com.eorder.service.ProductService;
import com.eorder.utils.CookieUtil;
import com.eorder.utils.ResultVOUtil;
import com.eorder.utils.SellerIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.redis.core.StringRedisTemplate;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卖家类目
 */

@RestController
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 查询类目
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<ProductCategory>> list(HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        List<ProductCategory> productCategoryList = categoryService.findAllBySellerId(sellerId);
        return ResultVOUtil.success(productCategoryList);
    }

    /**
     * 修改类目
     * @param categoryForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    public ResultVO update(@Valid CategoryForm categoryForm,
                           BindingResult bindingResult,
                           HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        try {
            if (bindingResult.hasErrors()) {
                log.error("【修改类目】参数不正确， CategoryForm={}", categoryForm);
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                        bindingResult.getFieldError().getDefaultMessage());
            }
            ProductCategory productCategory = categoryService.findOne(categoryForm.getCategoryId());
            if (productCategory == null) {
                log.error("【修改类目】类目不存在");
                throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
            }
            if (!productCategory.getSellerId().equals(sellerId)) {
                log.error("【修改类目】类目不属于当前用户");
                throw new SellException(ResultEnum.CATERGORY_OWNER_ERROR);
            }
            BeanUtils.copyProperties(categoryForm, productCategory);
            productCategory.setSellerId(sellerId);
            categoryService.save(productCategory);
            return ResultVOUtil.success();
        } catch (Exception e) {
            if (e instanceof SellException) {
                return ResultVOUtil.error(((SellException)e).getCode(), e.getMessage());
            } else {
                return ResultVOUtil.error(e.hashCode(), e.getMessage());
            }
        }
    }

    /**
     * 添加类目
     * @param categoryForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/add")
    public ResultVO add(@Valid CategoryForm categoryForm,
                        BindingResult bindingResult,
                        HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        try {
            if (bindingResult.hasErrors()) {
                log.error("【添加类目】参数不正确， CategoryForm={}", categoryForm);
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                        bindingResult.getFieldError().getDefaultMessage());
            }
            ProductCategory productCategory = new ProductCategory();
            BeanUtils.copyProperties(categoryForm, productCategory);
            productCategory.setSellerId(sellerId);
            categoryService.save(productCategory);
            Map<String, Integer> map = new HashMap<>();
            map.put("categoryId", productCategory.getCategoryId());
            return ResultVOUtil.success();
        } catch (Exception e ) {
            if (e instanceof SellException) {
                return ResultVOUtil.error(((SellException)e).getCode(), e.getMessage());
            } else {
                return ResultVOUtil.error(e.hashCode(), e.getMessage());
            }

        }
    }

    /**
     * 删除类目
     * @param categoryId
     * @return
     */
    @PostMapping("/delete")
    public ResultVO delete(@RequestParam("categoryId") Integer categoryId,
                           HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        try {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            if (productCategory == null) {
                log.error("【删除类目】类目不存在");
                throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
            }
            if (!productCategory.getSellerId().equals(sellerId)) {
                log.error("【删除类目】类目不属于当前用户");
                throw new SellException(ResultEnum.CATERGORY_OWNER_ERROR);
            }
            categoryService.delete(productCategory);
            return ResultVOUtil.success();
        } catch (Exception e) {
            if (e instanceof SellException) {
                return ResultVOUtil.error(((SellException)e).getCode(), e.getMessage());
            } else {
                return ResultVOUtil.error(e.hashCode(), e.getMessage());
            }
        }

    }
}
