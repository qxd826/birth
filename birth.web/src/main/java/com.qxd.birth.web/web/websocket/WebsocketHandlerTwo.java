package com.qxd.birth.web.web.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xiangqong.qu on 16/9/21 15:43.
 * <p>
 * websocket处理类
 */
@Component
@Slf4j
public class WebsocketHandlerTwo extends TextWebSocketHandler {

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    /**
     * after connection establish
     */
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.info("connect success... WebSocketHandlerTwo" + this.hashCode());
        users.add(webSocketSession);
    }

    /**
     * process the received message
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        sendMessageToUsers(new TextMessage(webSocketMessage.getPayload() + "hello"));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        log.info("connenction error,close the connection...");
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.info("close the connenction..." + closeStatus.toString());
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                } else {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
