package com.example.demo.socketConfig;

import org.springframework.context.annotation.Configuration; 
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
//SockJS 사용
public class SocketJsWebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic"); // /topic 으로 시작하는 destination 헤더를 가진 메세지를 브로커로 라우팅한다.
    config.setApplicationDestinationPrefixes("/app"); // /app 경로로 시작하는 STOMP 메세지의 destination 헤더는 @Controller 객체의 @MessageMapping 메서드로 라우팅된다.
  
  //enableSimpleBroker: 해당 경로로 SimpleBroker를 등록,SimpleBroker는 해당하는 경로를 SUBSCRIBE하는 Client에게 메세지를 전달하는 간단한 작업을 수행 
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/gs-guide-websocket").withSockJS();
  }

}