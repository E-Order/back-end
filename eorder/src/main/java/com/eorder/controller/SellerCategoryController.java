package com.eorder.controller;

import com.eorder.VO.ResultVO;
import com.eorder.dataobject.ProductCategory;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.form.CategoryForm;
import com.eorder.service.CategoryService;
import com.eorder.service.ProductService;
import com.eorder.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResultVO<List<ProductCategory>> list() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
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
                           BindingResult bindingResult) {
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
            BeanUtils.copyProperties(categoryForm, productCategory);
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
                        BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                log.error("【添加类目】参数不正确， CategoryForm={}", categoryForm);
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                        bindingResult.getFieldError().getDefaultMessage());
            }
            ProductCategory productCategory = new ProductCategory();
            BeanUtils.copyProperties(categoryForm, productCategory);
            categoryService.save(productCategory);
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
    public ResultVO delete(@RequestParam("categoryId") Integer categoryId) {
        try {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            if (productCategory == null) {
                log.error("【删除类目】类目不存在");
                throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
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