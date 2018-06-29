package com.eorder.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket/{token}")
@Slf4j
public class WebSocket {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private Session session;
    private String token;
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(@PathParam(value = "token") String param, Session session) {
        this.session = session;
        this.token = redisTemplate.opsForValue().get(String.format("token_%s", param));
        webSocketSet.add(this);
        log.info("【websocket消息】 有新的连接， 总数：{}", webSocketSet.size());
        log.info(this.token);

    }
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("【websocket消息】 连接断开， 总数：{}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】 收到客户端发来的消息：{}", message);
    }

    public void sendMessage(String message, String sellerId) {
        for (WebSocket webSocket : webSocketSet) {

            if (webSocket.token.equals(sellerId)) {
                log.info("【websocket消息】 发送消息， message={}",message);
                try {
                    webSocket.session.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
