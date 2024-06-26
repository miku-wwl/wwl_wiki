package com.weilai.wiki.service;

import com.weilai.wiki.websocket.WebSocketServer;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class WsService {

    @Resource
    public WebSocketServer webSocketServer;

    @Async
    public void sendInfo(String message) {
        webSocketServer.sendInfo(message);
    }
}