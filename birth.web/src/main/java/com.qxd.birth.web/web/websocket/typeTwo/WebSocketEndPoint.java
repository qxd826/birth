package com.qxd.birth.web.web.websocket.typeTwo;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by xiangqong.qu on 16/9/22 20:28.
 */
@ServerEndpoint("/webSocketTypeTwo")
@Slf4j
public class WebSocketEndPoint {
    /**
     * 打开连接时触发
     *
     * @param relationId
     * @param userCode
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("relationId") String relationId,
                       @PathParam("userCode") int userCode,
                       Session session) {
        log.info("WebSocket Start Connecting:" + SessionUtils.getKey(relationId, userCode));
        SessionUtils.put(relationId, userCode, session);
    }

    /**
     * 收到客户端消息时触发
     *
     * @param relationId
     * @param userCode
     * @param message
     *
     * @return
     */
    @OnMessage
    public String onMessage(@PathParam("relationId") String relationId,
                            @PathParam("userCode") int userCode,
                            String message) {
        return "Got your message (" + message + ").Thanks !";
    }

    /**
     * 异常时触发
     *
     * @param relationId
     * @param userCode
     * @param session
     */
    @OnError
    public void onError(@PathParam("relationId") String relationId,
                        @PathParam("userCode") int userCode,
                        Throwable throwable,
                        Session session) {
        log.info("WebSocket Connection Exception:" + SessionUtils.getKey(relationId, userCode));
        log.info(throwable.getMessage(), throwable);
        SessionUtils.remove(relationId, userCode);
    }

    /**
     * 关闭连接时触发
     *
     * @param relationId
     * @param userCode
     * @param session
     */
    @OnClose
    public void onClose(@PathParam("relationId") String relationId,
                        @PathParam("userCode") int userCode,
                        Session session) {
        log.info("WebSocket Close Connection:" + SessionUtils.getKey(relationId, userCode));
        SessionUtils.remove(relationId, userCode);
    }
}
