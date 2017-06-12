package com.qxd.birth.service.websocket;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

/**
 * Created by xiangqong.qu on 16/9/22 15:46.
 */
public class WebSocketUtils {
    //存储socket连接用户
    private static Map<String, WebSocketSession> sessionMap = new HashMap();

    public static synchronized void addSession(WebSocketSession webSocketSession) {
        if (null == sessionMap) {
            sessionMap = new HashMap<>();
        }
        sessionMap.put(webSocketSession.getId(), webSocketSession);
    }

    public static synchronized void deleteSessionById(String sessionId) {
        if (null == sessionMap) {
            return;
        }
        if (StringUtils.isBlank(sessionId)) {
            return;
        }
        if (sessionMap.containsKey(sessionId)) {
            sessionMap.remove(sessionId);
        }
        return;
    }

    public static synchronized List<WebSocketSession> getSessionList() {
        if (null == sessionMap) {
            return Collections.emptyList();
        }
        return new ArrayList<>(sessionMap.values());
    }

}
