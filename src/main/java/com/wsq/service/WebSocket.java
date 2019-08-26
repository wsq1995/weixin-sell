package com.wsq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author wsq
 * @date 2019/7/1 23:14
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
    }

    @OnClose
    public void onClose(){
        webSockets.remove(this);
    }

    @OnMessage
    public void onMessage(String msg){
        log.info("【webSocket】接受到客户端的消息", msg);
    }

    public void sendMessage(String msg){
        for (WebSocket websocket : webSockets) {
            log.info("【websocket广播消息】msg={}", msg);
            try {
                websocket.session.getBasicRemote().sendText(msg);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
