package com.example.demo.controller.webSocket_nettyTest;

import io.netty.bootstrap.Bootstrap;  
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
public EchoClient(){
}

public void start() throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
    	
     	//ServerBootstrap: 서버 애플리케이션을 위한 부트스트랩
    	//Bootstrap: 클라이언트를 위한 부트스트랩
        Bootstrap b = new Bootstrap();  // bootstrap 생성
        b.group(group)  // 클라이언트 이벤트 처리할 EventLoopGroup을 지정.
                .channel(NioSocketChannel.class)    // 채널 유형 NIO 지정
                .remoteAddress(new InetSocketAddress("192.168.0.62",8082)) // 서버의 InetSocketAddress를 설정하면 내부적으로 연결 시도
                .handler(new ChannelInitializer<SocketChannel>() { //핸들러 등록, ChannelInitializer은 ChannelInboundHandlerAdapter 상속 받고있는데
                	//ChannelInboundHandlerAdapter를 상속받고있는 커스텀핸들러를 생성하면 유저가 내부 행동을 조작할수있음
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {    // 채널이 생성될 때 파이프라인에 EchoClientHandler 하나를 추가
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });
        ChannelFuture f = b.connect().sync();   // 원격 피어로 연결하고 연결이 완료되기를 기다림
        f.channel().closeFuture().sync();   // 채널이 닫힐 때까지 블로킹함.
    } finally {
        group.shutdownGracefully().sync();  // 스레드 풀을 종료하고 모든 리소스를 해제함
    }
}

public static void main(String[] args) throws Exception {
    new EchoClient().start();
}
}