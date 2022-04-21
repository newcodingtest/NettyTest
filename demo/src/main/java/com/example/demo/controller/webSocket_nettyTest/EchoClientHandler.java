package com.example.demo.controller.webSocket_nettyTest;

import java.nio.charset.Charset;  
import java.util.Scanner;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//inbound handler: �Էµ����Ϳ� ���� ������¸� �����ϰ� ó���ϴ� �ڵ鷯
//outbound handler: ��µ����͵� ���� ������ ����ä ó���ϴ� �ڵ鷯
public class EchoClientHandler extends ChannelInboundHandlerAdapter  {
	
	 //ä���� Ȱ��ȭ �Ǹ� ����
      @Override
      public void channelActive(ChannelHandlerContext ctx){

    	  while(true) {
    	 //�Է¹��� ���ڿ�
        String sendMessage = new Scanner(System.in).nextLine();

        ByteBuf messageBuffer = Unpooled.buffer();
        
        //�Է¹��� ���ڿ��� ����Ʈ�� ��ȯ�Ͽ� ����Ʈ���ۿ� ��´�.
        messageBuffer.writeBytes(sendMessage.getBytes());

        StringBuilder builder = new StringBuilder();
        builder.append("����: ");
        builder.append(sendMessage);

        System.out.println(builder.toString());
        ctx.writeAndFlush(messageBuffer);
    	  }
      }

      //ä���� inbound buffer���� ���� ���� ������ �д´�.
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg){

        String readMessage = ((ByteBuf)msg).toString(Charset.defaultCharset());

        StringBuilder builder = new StringBuilder();
        builder.append("����: ");
        builder.append(readMessage);

        System.out.println(builder.toString());

      }

      //ä�� �б� ���۽�(channelRead Ȱ��ȭ��) ȣ���
      @Override
      public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.close();
      }

      //���� �߻��� ȣ���
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
      }
}