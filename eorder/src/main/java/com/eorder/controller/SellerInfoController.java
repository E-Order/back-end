package com.eorder.controller;

import com.eorder.VO.ResultVO;
import com.eorder.dataobject.SellerInfo;
import com.eorder.dto.SellerDTO;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.form.SellerForm;
import com.eorder.service.SellerService;
import com.eorder.utils.ResultVOUtil;
import com.eorder.utils.SellerIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/seller")
@Slf4j
public class SellerInfoController {

    @Autowired
    SellerService sellerService;
    /**
     * 查询商家信息
     * @return
     */
    @GetMapping("/info")
    public ResultVO info(HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        try {
            SellerDTO sellerDTO = sellerService.findSeller(sellerId);

            return ResultVOUtil.success(sellerDTO);
        } catch (Exception e) {
            if (e instanceof SellException) {
                return ResultVOUtil.error(((SellException)e).getCode(), e.getMessage());
            } else {
                return ResultVOUtil.error(e.hashCode(), e.getMessage());
            }
        }
    }

    /**
     * 修改商家信息
     * @param sellerForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    public ResultVO login(@Valid SellerForm sellerForm,
                          BindingResult bindingResult,
                          HttpServletRequest request) {
        String sellerId = SellerIdUtil.getSellerId(request);
        try {
            if (bindingResult.hasErrors()) {
                log.error("【修改商家】参数不正确， SellerForm={}", sellerForm);
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                        bindingResult.getFieldError().getDefaultMessage());
            }
            SellerInfo sellerInfo = new SellerInfo();
            BeanUtils.copyProperties(sellerForm, sellerInfo);
            sellerInfo = sellerService.update(sellerInfo, sellerId);
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
