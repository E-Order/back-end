package com.eorder.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jndi.toolkit.url.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sun.net.util.URLUtil;
import sun.net.www.URLConnection;

import javax.xml.transform.Result;
import java.io.*;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/start")
    public String start(@RequestParam("code") String code) {
        //微信端登录code值
        String appid = "wx3cf64998f8d0620d";
        String secret = "45309d94006144ae9bbfc4eeeec71a9f";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }
}