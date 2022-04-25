package com.example.demo.controller.webSocket_nettyTest;

import java.nio.charset.Charset;  
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

//inbound handler: 입력데이터에 대한 변경상태를 감시하고 처리하는 핸들러
//outbound handler: 출력데이터데 대한 동작을 가로채 처리하는 핸들러
@Slf4j
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	
	private EchoClient nettyClient;
	private static boolean connectionFlag;
	
	public EchoClientHandler() {
		nettyClient = new EchoClient();
	}
	
	 //채널이 활성화 되면 동작
    @Override
    public void channelActive(ChannelHandlerContext ctx){
    	System.out.println("서버와의 채널이 연결되었음");
    	connectionFlag = true;
    	
	  	//while(connectionFlag) {
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
	//  	  }
    }

    /**
     * 재접속 처리[1번째 방법] https://emong.tistory.com/160
		1.클라이언트가 처리하는 동안 연결이 끊어졌습니다. -->단순 채널만 닫힌경우, 서버가 다운되지는 않은 상태
     * */
    @Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("서버와의 채널이 닫혔어");
    	connectionFlag=false;
		log.info("Disconnected from the server, try to reconnect...");
	   //reconnect immediately
		final EventLoop loop = ctx.channel().eventLoop();
		loop.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					nettyClient.start();
			    	connectionFlag = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			    	connectionFlag=false;
				}
			}
		}, 1L, TimeUnit.SECONDS);

		super.channelInactive(ctx);  
	}
    
    /**
	 * operationComplete: 연결 처리 후 서버와의 상태값을 얻어 올 수 있음
	 *  재접속 처리[2번째 방법] 클라이언트가 시작 시 연결되지 않음 -->채널과 서버 둘다 다운된 상태
	 */
    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
    	System.out.println("서버가 클라이언트와의 접속이 끊겼다.");
    	//1초 주기로 재접속을 시도한다.
        ctx.channel().eventLoop().schedule(() -> {
        	try {
        		System.out.println("재접속을 시도하자");
				nettyClient.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }, 1L, TimeUnit.SECONDS);
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

    //채 읽기 동작시(channelRead 활성화시) 호출됨
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
      ctx.close();
    }

    //오류 발생시 호출됨
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
    	System.out.println("오류발생");
      cause.printStackTrace();
      ctx.close();
    }


}