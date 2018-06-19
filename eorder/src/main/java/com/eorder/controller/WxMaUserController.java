package com.eorder.controller;

import com.eorder.utils.HttpUtils;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;


import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;





import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class WxMaUserController {
    @Value("${weixin.app_id}") // yml配置appID,这里还没写
    private String appId;

   /@Value("${weixin.app_secret}") // yml配置secret,这里还没写
    private String secret;

    @RequestMapping("/openid")
    @ResponseBody
    public Map<String, Object> openid(String code){ // 小程序端获取的CODE
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        try {
            boolean check = (StringUtils.isEmpty(code)) ? true : false;
            if (check) {
                throw new Exception("参数异常");
            }
            StringBuilder urlPath = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session"); // 微信提供的API，这里最好也放在配置文件
            urlPath.append(String.format("?appid=%s", appId));
            urlPath.append(String.format("&secret=%s", secret));
            urlPath.append(String.format("&js_code=%s", code));
            urlPath.append(String.format("&grant_type=%s", "authorization_code")); // 固定值
            String data = HttpUtils.sendPost(urlPath.toString(),""); // java的网络请求，这里是我自己封装的一个工具包，返回字符串
            System.out.println("请求结果：" + data);
            String openid = new JSONObject(data).getString("openid");
            System.out.println("获得openid: " + openid);
            result.put("openid", openid);
        } catch (Exception e) {
            result.put("code", 1);
            result.put("remark", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
