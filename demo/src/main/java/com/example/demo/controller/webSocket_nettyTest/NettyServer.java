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
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); //Ŭ���̾�Ʈ�� ������ �����ϴ� �θ𽺷��� �׷�, NioEventLoopGroup(1): �ִ� ����������� 1�� ����
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //����� Ŭ���̾�Ʈ �������κ��� ������ ����� �� �̺�Ʈ ó�� ���
        try {
        	
        	//ServerBootstrap: ���� ���ø����̼��� ���� ��Ʈ��Ʈ��
        	//Bootstrap: Ŭ���̾�Ʈ�� ���� ��Ʈ��Ʈ��
            ServerBootstrap b = new ServerBootstrap();
            
            //��Ʈ��Ʈ���� ���� �������� �����Ǿ� �־ �پ��� ������ ������
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup) //��ü �ʱ�ȭ ����
                    .channel(NioServerSocketChannel.class) //���� ������ ����� ����� ��� ����,  NioServerSocketChannel: ����ŷ ����� ���� ���� ä���� �����ϴ� Ŭ����
                    .childHandler(new ChannelInitializer<Channel>() { //Ŭ���̾�Ʈ�� ���� ����� ä���� �ʱ�ȭ�ɶ� �⺻������ ������ �߻�Ŭ����

						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(new NettySocketHandler()); // ä���� ���������ο� �ڵ鷯 ���, WebSocketHandler: Ŭ���̾�Ʈ ������ �����Ǿ��� ��� ������ ó�� ���
							
						}
					});

            //��Ʈ�� ������ ���ε��ص� �������� �ʴ´�
            //https://groups.google.com/g/netty-ko/c/-9FKO0lyDPc
            //https://groups.google.com/g/netty-ko/c/2dAGfzoqTpw
            //��Ƽ��Ʈ ���ε����
            //https://blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=hsunryou&logNo=220919284980
            ChannelFuture ch = b.bind(PORT).sync(); 	//������ �񵿱� ������ ���ε� �Ѵ�. sync() �� ���ε��� �Ϸ�Ǳ⸦ ����Ѵ�.
            ChannelFuture ch1 = b.bind(8081).sync(); 	//������ �񵿱� ������ ���ε� �Ѵ�. sync() �� ���ε��� �Ϸ�Ǳ⸦ ����Ѵ�.
            
            
            ch.channel().closeFuture().sync(); //ä���� CloseFuture�� ��� �Ϸ� �ɶ� ���� ���� �����带 ���ŷ�Ѵ�.
            ch1.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        	// Group �� ���� �ϰ� ��� ���ҽ��� ���� �Ѵ�.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
  }
  

}