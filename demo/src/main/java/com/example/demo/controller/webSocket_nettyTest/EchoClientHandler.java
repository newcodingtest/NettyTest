package com.example.demo.controller.webSocket_nettyTest;

import java.nio.charset.Charset;  
import java.util.Scanner;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//inbound handler: 입력데이터에 대한 변경상태를 감시하고 처리하는 핸들러
//outbound handler: 출력데이터데 대한 동작을 가로채 처리하는 핸들러
public class EchoClientHandler extends ChannelInboundHandlerAdapter  {
	
	 //채널이 활성화 되면 동작
      @Override
      public void channelActive(ChannelHandlerContext ctx){

    	  while(true) {
    	 //입력받은 문자열
        String sendMessage = new Scanner(System.in).nextLine();

        ByteBuf messageBuffer = Unpooled.buffer();
        
        //입력받은 문자열을 바이트로 전환하여 바이트버퍼에 담는다.
        messageBuffer.writeBytes(sendMessage.getBytes());

        StringBuilder builder = new StringBuilder();
        builder.append("전송: ");
        builder.append(sendMessage);

        System.out.println(builder.toString());
        ctx.writeAndFlush(messageBuffer);
    	  }
      }

      //채널의 inbound buffer에서 읽을 값이 있으면 읽는다.
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg){

        String readMessage = ((ByteBuf)msg).toString(Charset.defaultCharset());

        StringBuilder builder = new StringBuilder();
        builder.append("수신: ");
        builder.append(readMessage);

        System.out.println(builder.toString());

      }

      //채넑 읽기 동작시(channelRead 활성화시) 호출됨
      @Override
      public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.close();
      }

      //오류 발생시 호출됨
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
      }
}