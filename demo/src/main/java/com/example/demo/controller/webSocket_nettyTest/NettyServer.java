package com.example.demo.controller.webSocket_nettyTest;

import io.netty.bootstrap.ServerBootstrap;  
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    private static final int PORT = 8082;

  public void run() {
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); //클라이언트의 연결을 수락하는 부모스레드 그룹, NioEventLoopGroup(1): 최대 생성스레드수 1개 설정
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //연결된 클라이언트 소켓으로부터 데이터 입출력 및 이벤트 처리 담당
        try {
        	
        	//ServerBootstrap: 서버 애플리케이션을 위한 부트스트랩
        	//Bootstrap: 클라이언트를 위한 부트스트랩
            ServerBootstrap b = new ServerBootstrap();
            
            //부트스트랩은 빌더 패턴으로 구현되어 있어서 다양한 설정이 가능함
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup) //객체 초기화 시작
                    .channel(NioServerSocketChannel.class) //서버 소켓이 사용할 입출력 모드 설정,  NioServerSocketChannel: 논블로킹 모드의 서버 소켓 채널을 생성하는 클래스
                    .childHandler(new ChannelInitializer<Channel>() { //클라이언트로 부터 연결된 채널이 초기화될때 기본동작이 지정된 추상클래스

						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(new NettySocketHandler()); // 채널의 파이프라인에 핸들러 등록, WebSocketHandler: 클라이언트 연결이 생성되었을 경우 데이터 처리 담당
							
						}
					});

            //포트를 여러개 바인딩해도 문제되지 않는다
            //https://groups.google.com/g/netty-ko/c/-9FKO0lyDPc
            //https://groups.google.com/g/netty-ko/c/2dAGfzoqTpw
            //멀티포트 바인딩방법
            //https://blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=hsunryou&logNo=220919284980
            ChannelFuture ch = b.bind(PORT).sync(); 	//서버를 비동기 식으로 바인딩 한다. sync() 는 바인딩이 완료되기를 대기한다.
            ChannelFuture ch1 = b.bind(8081).sync(); 	//서버를 비동기 식으로 바인딩 한다. sync() 는 바인딩이 완료되기를 대기한다.
            
            
            ch.channel().closeFuture().sync(); //채널의 CloseFuture를 얻고 완료 될때 까지 현재 스레드를 블로킹한다.
            ch1.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        	// Group 을 종료 하고 모든 리소스를 해제 한다.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
  }
  

}