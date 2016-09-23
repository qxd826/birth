package com.qxd.birth.web.web.websocket.typeOne;

import com.qxd.birth.service.websocket.WebSocketFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;


/**
 * Created by xiangqong.qu on 16/9/21 15:43.
 * <p>
 * websocket处理类
 */
@Slf4j
@Component
public class WebsocketHandlerOne extends TextWebSocketHandler {

    @Resource
    private WebSocketFacade webSocketFacade;

    /**
     * after connection establish
     */
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.info("connect success... WebSocketHandlerOne" + this.hashCode());
        webSocketFacade.addSession(webSocketSession);
    }

    /**
     * process the received message
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        webSocketFacade.sendMessageToAll(webSocketMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        log.info("connenction error,close the connection...");
        webSocketFacade.deleteSessionBySessionId(webSocketSession.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.info("close the connenction..." + closeStatus.toString());
        webSocketFacade.deleteSessionBySessionId(webSocketSession.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
