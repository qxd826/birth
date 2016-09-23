package com.qxd.birth.web.web.websocket.typeOne;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by xiangqong.qu on 16/9/22 14:55.
 * <p>
 * socket 服务端的设置 可以通过 该类来配置
 * 或
 * 通过xml注解的方式配置  见 websocket-service.xml
 * <p>
 * Configuration 会造成 一些依赖的bean还没注入
 */
@EnableWebMvc
@EnableWebSocket
@Slf4j
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private WebsocketHandlerTwo websocketHandlerTwo;

    private HandshakeInterceptor handshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("websocket 注册");
        websocketHandlerTwo = new WebsocketHandlerTwo();
        handshakeInterceptor = new HandshakeInterceptor();

        /**
         * 支持websocket 的 connection
         */
        registry.addHandler(websocketHandlerTwo, "/webSocketTwo").addInterceptors(handshakeInterceptor);

        /**
         * 不支持websocket的connenction,采用sockjs
         */
        registry.addHandler(websocketHandlerTwo, "/sockJs/webSocketTwo").addInterceptors(handshakeInterceptor).withSockJS();
    }
}
