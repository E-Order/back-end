package com.eorder.controller;

import com.eorder.VO.ResultVO;
import com.eorder.dataobject.SellerInfo;
import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.form.SellerForm;
import com.eorder.service.SellerService;
import com.eorder.utils.CookieUtil;
import com.eorder.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家
 */

@RestController
@RequestMapping("seller")
@Slf4j
public class SellerUserController {

    @Autowired
    SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 商家注册
     * @param sellerForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/signup")
    public ResultVO signup(@Valid SellerForm sellerForm,
                           BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                log.error("【商家注册】参数不正确， SellerForm={}", sellerForm);
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                        bindingResult.getFieldError().getDefaultMessage());
            }
            SellerInfo sellerInfo = new SellerInfo();
            BeanUtils.copyProperties(sellerForm, sellerInfo);
            sellerInfo = sellerService.create(sellerInfo);
            Map<String, String> map = new HashMap<>();
            map.put("sellerId", sellerInfo.getSellerId());
            return ResultVOUtil.success(map);
        } catch (Exception e) {
            if (e instanceof SellException) {
                return ResultVOUtil.error(((SellException)e).getCode(), e.getMessage());
            } else {
                return ResultVOUtil.error(e.hashCode(), e.getMessage());
            }
        }

    }

    /**
     * 商家登录
     * @param username
     * @param password
     * @param response
     * @return
     */
    @PostMapping("/signin")
    public ResultVO signin(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           HttpServletResponse response) {
        try {
            //1.进行用户名与密码匹配
            SellerInfo sellerInfo = sellerService.findSeller(username,password);
            //2.设置token至redis
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(String.format("token_%s", token), sellerInfo.getSellerId(), 2, TimeUnit.HOURS);
            CookieUtil.set(response,"token", token,7200);
            return ResultVOUtil.success();
        } catch (Exception e) {
            if (e instanceof SellException) {
                return ResultVOUtil.error(((SellException)e).getCode(), e.getMessage());
            } else {
                return ResultVOUtil.error(e.hashCode(), e.getMessage());
            }
        }

    }

    @GetMapping("/signout")
    public ResultVO signout(HttpServletRequest request,
                            HttpServletResponse response) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, "token");
        if (cookie != null) {
            //2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format("token_%s", cookie.getValue()));
            //3. 清除cookie
            CookieUtil.set(response, "token", null, 0);
        }
        return ResultVOUtil.success();
    }



}
