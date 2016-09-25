package com.qxd.birth.web.web.websocket.typeTwo;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiangqong.qu on 16/9/22 20:37.
 */
public class SessionUtils {


    public static Map<String, Session> clients = new ConcurrentHashMap<>();

    public static void put(String relationId, int userCode, Session session) {
        clients.put(getKey(relationId, userCode), session);
    }

    public static Session get(String relationId, int userCode) {
        return clients.get(getKey(relationId, userCode));
    }

    public static void remove(String relationId, int userCode) {
        String key = getKey(relationId, userCode);
        if (clients.containsKey(key)) {
            clients.remove(key);
        }
    }

    /*
     * 判断是否有连接
     *
     * @param relationId
     * @param userCode
     *
     * @return
     */

    public static boolean hasConnection(String relationId, int userCode) {
        return clients.containsKey(getKey(relationId, userCode));
    }

    /*
     * 组装唯一识别的key
     *
     * @param relationId
     * @param userCode
     *
     * @return
      */
    public static String getKey(String relationId, int userCode) {
        return relationId + "_" + userCode;
    }
}
