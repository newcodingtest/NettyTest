package com.example.demo.controller;

import javax.annotation.PostConstruct; 
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Controller;

import com.example.demo.controller.webSocket_nettyTest.NettyServer;


@Controller
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
	private void destory() {
	}
}
