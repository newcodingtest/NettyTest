package com.example.demo.controller.webSocket_nettyTest;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.PingMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent; 



@Sharable
public class NettySocketHandler extends ChannelInboundHandlerAdapter {
	
	private InterfaceNetwork network = InterfaceNetwork.INSTANCE.getInstance();
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    
            String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
            
            //내부적으로 기록과 전송의 두가지 메서드 실행, write():채널에 데이터 기록, flush():채널에 기록된 데이터 서버로전송
            //flush로 채널에 있던것을 서버로 전송했기때문에 추구 syso로 출력해볼수 있는것이다.
            //write만 썻을때는 콘솔에 출력이 안됨을 확인하였음
            //ctx.writeAndFlush(msg);
            ctx.write(msg);
            //3. 수신된 데이터를 가지고 있는 네티의 바이트 버퍼 객체로 부터 문자열 객체를 읽어온다.
            //System.out.println("수신한 문자열 ["+readMessage +"]");
           
        }
	
	   //채널이 활성화 되면 자동으로 호출(ex.클라이언트가 서버에 연결했을경우)
		@Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
	        //ctx.fireChannelActive();
			System.out.println("채널이 활성화되었습니다.");
			
			Channel income = ctx.channel();
			InetSocketAddress localAddress = (InetSocketAddress)income.localAddress();
			System.out.println(localAddress.getPort());
			changeNetworkTrue(localAddress.getPort());
			
			System.out.println(getStatus());
	    }
    
	   //채널이 비 활성화 되면 자동으로 호출(ex.클라이언트가 서버에 연결을 끊었을경우)
	   //고민거리
	   //멀티 클라이언트에서 연결중인데 그중 하나가 끊겼을시 어떤 클라이언트가 끊겼는지 어떻게 판단해줄것인가?
	   //https://groups.google.com/g/netty-ko/c/XlpMAhPGu9M 참고
	   @Override
	    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	       // ctx.fireChannelInactive();
		   System.out.println("채널이 비 활성화되었습니다.");
		   
			Channel income = ctx.channel();
			InetSocketAddress localAddress = (InetSocketAddress)income.localAddress();
			System.out.println(localAddress.getPort());
			changeNetworkFalse(localAddress.getPort());
			System.out.println(getStatus());
	    } 
	   
    
    //6. channelRead 이벤트의 처리가 완료된 후 자동으로 수행되는 이벤트 메서드
    /* @Override
    public void channelReadComplete(ChannelHandlerContext ctx){

   
      ctx.flush();
      //7. 채널 파이프 라인에 저장된 버퍼를 전송
    }
   */
	   
    /*
     채널에 예외 터졋을때 호출된다.
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      cause.printStackTrace();
      ctx.close();
    }
     */

	  private void changeNetworkTrue(int port) {
		  network.changeNetworkTrue(port);
	  }
	  
	  private void changeNetworkFalse(int port) {
		  network.changeNetworkFalse(port);
	  }
	  
		public ConcurrentHashMap<String, String> getStatus(){
			return network.getStatus();
		}
    
    
}