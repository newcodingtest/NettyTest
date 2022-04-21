package com.example.demo.socketConfig;

import org.springframework.context.annotation.Configuration; 
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
//SockJS ���
public class SocketJsWebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic"); // /topic ���� �����ϴ� destination ����� ���� �޼����� ���Ŀ�� ������Ѵ�.
    config.setApplicationDestinationPrefixes("/app"); // /app ��η� �����ϴ� STOMP �޼����� destination ����� @Controller ��ü�� @MessageMapping �޼���� ����õȴ�.
  
  //enableSimpleBroker: �ش� ��η� SimpleBroker�� ���,SimpleBroker�� �ش��ϴ� ��θ� SUBSCRIBE�ϴ� Client���� �޼����� �����ϴ� ������ �۾��� ���� 
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/gs-guide-websocket").withSockJS();
  }

}