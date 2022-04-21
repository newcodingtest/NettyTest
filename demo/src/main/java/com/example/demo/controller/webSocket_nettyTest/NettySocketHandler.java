package com.example.demo.controller.webSocket_nettyTest;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter; 



@Sharable
public class NettySocketHandler extends ChannelInboundHandlerAdapter {
	
	private InterfaceNetwork network = InterfaceNetwork.INSTANCE.getInstance();
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    
/*        if (msg instanceof WebSocketFrame) {
            System.out.println("This is a WebSocket frame");
            System.out.println("Client Channel : " + ctx.channel());
            if (msg instanceof BinaryWebSocketFrame) {
                System.out.println("BinaryWebSocketFrame Received : ");
                System.out.println(((BinaryWebSocketFrame) msg).content());
            } else if (msg instanceof TextWebSocketFrame) {
                System.out.println("TextWebSocketFrame Received : ");
                ctx.channel().writeAndFlush(
                        new TextWebSocketFrame("Message recieved : " + ((TextWebSocketFrame) msg).text()));
                System.out.println(((TextWebSocketFrame) msg).text());
            } else if (msg instanceof PingWebSocketFrame) {
                System.out.println("PingWebSocketFrame Received : ");
                System.out.println(((PingWebSocketFrame) msg).content());
            } else if (msg instanceof PongWebSocketFrame) {
                System.out.println("PongWebSocketFrame Received : ");
                System.out.println(((PongWebSocketFrame) msg).content());
            } else if (msg instanceof CloseWebSocketFrame) {
                System.out.println("CloseWebSocketFrame Received : ");
                System.out.println("ReasonText :" + ((CloseWebSocketFrame) msg).reasonText());
                System.out.println("StatusCode : " + ((CloseWebSocketFrame) msg).statusCode());
            } else {
                System.out.println("Unsupported WebSocketFrame");
            }*/
            String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
            
            //���������� ��ϰ� ������ �ΰ��� �޼��� ����, write():ä�ο� ������ ���, flush():ä�ο� ��ϵ� ������ ����������
            //flush�� ä�ο� �ִ����� ������ �����߱⶧���� �߱� syso�� ����غ��� �ִ°��̴�.
            //write�� �������� �ֿܼ� ����� �ȵ��� Ȯ���Ͽ���
            ctx.writeAndFlush(msg);
            //3. ���ŵ� �����͸� ������ �ִ� ��Ƽ�� ����Ʈ ���� ��ü�� ���� ���ڿ� ��ü�� �о�´�.
            System.out.println("������ ���ڿ� ["+readMessage +"]");
           
        }
	
	   //ä���� Ȱ��ȭ �Ǹ� �ڵ����� ȣ��(ex.Ŭ���̾�Ʈ�� ������ �����������)
		@Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
	        //ctx.fireChannelActive();
			System.out.println("ä���� Ȱ��ȭ�Ǿ����ϴ�.");
			
			Channel income = ctx.channel();
			InetSocketAddress localAddress = (InetSocketAddress)income.localAddress();
			System.out.println(localAddress.getPort());
			changeNetworkTrue(localAddress.getPort());
			
			System.out.println(getStatus());
	    }
    
	   //ä���� �� Ȱ��ȭ �Ǹ� �ڵ����� ȣ��(ex.Ŭ���̾�Ʈ�� ������ ������ ���������)
	   //��ΰŸ�
	   //��Ƽ Ŭ���̾�Ʈ���� �������ε� ���� �ϳ��� �������� � Ŭ���̾�Ʈ�� ������� ��� �Ǵ����ٰ��ΰ�?
	   //https://groups.google.com/g/netty-ko/c/XlpMAhPGu9M ����
	   @Override
	    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	       // ctx.fireChannelInactive();
		   System.out.println("ä���� �� Ȱ��ȭ�Ǿ����ϴ�.");
		   
			Channel income = ctx.channel();
			InetSocketAddress localAddress = (InetSocketAddress)income.localAddress();
			System.out.println(localAddress.getPort());
			changeNetworkFalse(localAddress.getPort());
			System.out.println(getStatus());
	    }  
    
    //6. channelRead �̺�Ʈ�� ó���� �Ϸ�� �� �ڵ����� ����Ǵ� �̺�Ʈ �޼���
    /* @Override
    public void channelReadComplete(ChannelHandlerContext ctx){

   
      ctx.flush();
      //7. ä�� ������ ���ο� ����� ���۸� ����
    }
   */
    
    /*
     ä�ο� ���� �͠����� ȣ��ȴ�.
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