package com.qxd.birth.service.websocket.impl;

import com.qxd.birth.common.common.Result;
import com.qxd.birth.service.websocket.WebSocketFacade;
import com.qxd.birth.service.websocket.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by xiangqong.qu on 16/9/22 15:44.
 */
@Service
@Slf4j
public class WebSocketFacadeImpl implements WebSocketFacade {

/*    static {
        log.info("WebSocketFacadeImpl 注册");
    }


    @Override
    public Result<Boolean> addSession(WebSocketSession webSocketSession) {
        WebSocketUtils.addSession(webSocketSession);
        return Result.wrapSuccessfulResult(Boolean.TRUE);
    }

    @Override
    public Result<Boolean> deleteSessionBySessionId(String sessionId) {
        WebSocketUtils.deleteSessionById(sessionId);
        return Result.wrapSuccessfulResult(Boolean.TRUE);
    }

    @Override
    public Result<Boolean> sendMessageToAll(WebSocketMessage<?> webSocketMessage) {
        List<WebSocketSession> webSocketSessionList = WebSocketUtils.getSessionList();
        int countAll = 0;
        int countSend = 0;
        try {
            for (WebSocketSession webSocketSession : webSocketSessionList) {
                if (webSocketSession.isOpen()) {
                    countAll++;
                    webSocketSession.sendMessage(new TextMessage(webSocketMessage.getPayload() + ""));
                    countSend++;
                }
            }
        } catch (IOException e) {
            log.error("[发送消息] e:", e);
        }
        log.info(MessageFormat.format("总连接数{0},发送成功{1}", countAll, countSend));
        return Result.wrapSuccessfulResult(Boolean.TRUE);
    }*/
}
