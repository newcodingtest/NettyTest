package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping; 
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.DbInfoDTO;
import com.example.demo.service.DbInfoService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DbCheckController {

	private final DbInfoService service;
	
	@GetMapping("/dbTestPage")
	public void test() {
		
	}

	@MessageMapping("/dbcheck")
	@SendTo("/topic/db")
	public DbInfoDTO check()  throws Exception {
		 Thread.sleep(1000); // simulated delay
		 
		 return service.checkDbInfo();
	}
}
