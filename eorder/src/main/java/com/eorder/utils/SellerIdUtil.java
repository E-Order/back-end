package com.eorder.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
@Component
public class SellerIdUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static SellerIdUtil sellerIdUtil;
    @PostConstruct
    public void init() {
        sellerIdUtil = this;
        sellerIdUtil.redisTemplate = this.redisTemplate;
    }
    public static String getSellerId(HttpServletRequest request) {

        Cookie cookie = CookieUtil.get(request, "token");
        return sellerIdUtil.redisTemplate.opsForValue().get(String.format("token_%s", cookie.getValue()));
    }
}
