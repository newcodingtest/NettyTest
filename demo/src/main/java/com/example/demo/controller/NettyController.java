package com.example.demo.controller;

import javax.annotation.PostConstruct; 
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Controller;

import com.example.demo.controller.webSocket_nettyTest.NettyServer;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class NettyController {

	private NettyServer server;
	
	@PostConstruct
	private void start() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					server=new NettyServer();
					server.run();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	@PreDestroy
	private void destory() throws InterruptedException {
		log.info("서버종료");
		server.serverStop();
	}
}
