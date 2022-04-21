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
    	
     	//ServerBootstrap: ���� ���ø����̼��� ���� ��Ʈ��Ʈ��
    	//Bootstrap: Ŭ���̾�Ʈ�� ���� ��Ʈ��Ʈ��
        Bootstrap b = new Bootstrap();  // bootstrap ����
        b.group(group)  // Ŭ���̾�Ʈ �̺�Ʈ ó���� EventLoopGroup�� ����.
                .channel(NioSocketChannel.class)    // ä�� ���� NIO ����
                .remoteAddress(new InetSocketAddress("192.168.0.62",8082)) // ������ InetSocketAddress�� �����ϸ� ���������� ���� �õ�
                .handler(new ChannelInitializer<SocketChannel>() { //�ڵ鷯 ���, ChannelInitializer�� ChannelInboundHandlerAdapter ��� �ް��ִµ�
                	//ChannelInboundHandlerAdapter�� ��ӹް��ִ� Ŀ�����ڵ鷯�� �����ϸ� ������ ���� �ൿ�� �����Ҽ�����
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {    // ä���� ������ �� ���������ο� EchoClientHandler �ϳ��� �߰�
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });
        ChannelFuture f = b.connect().sync();   // ���� �Ǿ�� �����ϰ� ������ �Ϸ�Ǳ⸦ ��ٸ�
        f.channel().closeFuture().sync();   // ä���� ���� ������ ���ŷ��.
    } finally {
        group.shutdownGracefully().sync();  // ������ Ǯ�� �����ϰ� ��� ���ҽ��� ������
    }
}

public static void main(String[] args) throws Exception {
    new EchoClient().start();
}
}