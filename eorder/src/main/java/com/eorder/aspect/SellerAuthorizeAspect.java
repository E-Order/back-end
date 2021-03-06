package com.eorder.aspect;


import com.eorder.enums.ResultEnum;
import com.eorder.exception.SellException;
import com.eorder.exception.SellerException;
import com.eorder.utils.CookieUtil;
import com.eorder.utils.ResultVOUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户认证
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Pointcut("execution(public * com.eorder.controller.Seller*.*(..))" +
    "&& !execution(public * com.eorder.controller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public String doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
            //查询cookie
            Cookie cookie = CookieUtil.get(request, "token");
            if (cookie == null) {
                log.warn("【登录校验】Cookie中查不到token");
                throw new SellerException();
            }

            //去redis里查询
            String tokenValue = redisTemplate.opsForValue().get(String.format("token_%s", cookie.getValue()));
            if (StringUtils.isEmpty(tokenValue)) {
                log.warn("【登录校验】Redis中查不到token");
                throw new SellerException();
            }
            return tokenValue;

    }
}
