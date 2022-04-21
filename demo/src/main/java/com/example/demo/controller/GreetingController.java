package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.controller.webSocket_nettyTest.InterfaceNetwork;
import com.example.demo.dto.PendigDTO;
import com.example.demo.dto.RecvDTO;
import com.example.demo.service.HardWareCheckService;
import com.example.demo.service.PendingService;
import com.example.demo.service.RecvService;
import com.example.demo.service.SendService;
import com.example.demo.service.TestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GreetingController {

	private final TestService service;
	private final RecvService recvService;
	private final SendService sendService;
	private final PendingService pendService;
	private final HardWareCheckService hardWareService;
	
	private InterfaceNetwork network = InterfaceNetwork.getInstance();

	@GetMapping("/SockjsWebsocket")
	public void index() {
	
	}
	
	
  @MessageMapping("/hello1")
  @SendTo("/topic/greeting1") //Ŭ���̾�Ʈ���� �����ϴ� ���
  public List<RecvDTO> greeting1() throws Exception {
    Thread.sleep(1000); // simulated delay
    
    log.info("��Ʈ�ѷ�");
 
    return service.find();
  }
  
  @MessageMapping("/hello2")
  @SendTo("/topic/recv")
  public List<RecvDTO> greeting2() throws Exception {
    Thread.sleep(1000); // simulated delay
    
    log.info("����");
 
    return recvService.find();
  }
  
  @MessageMapping("/hello3")
  @SendTo("/topic/pending")
  public List<PendigDTO> greeting3() throws Exception {
    Thread.sleep(1000); // simulated delay
    
    //log.info("�ҵ�");
 
    return pendService.find();
  }
  
  @MessageMapping("/hello4")
  @SendTo("/topic/heartbeat")
  public ConcurrentHashMap<String,String> greeting4() throws Exception {
	 
	  log.info("������ ��Ʈ��ũ üũ: {}",network.getStatus());
	  	  
	  return network.getStatus();
  }
  
  @MessageMapping("/memory")
  @SendTo("/topic/memory")
  public String greeting5() throws Exception {
	 
	  log.info("������ ��Ʈ��ũ üũ: {}",hardWareService.getMemoryStatus());
	  	  
	  return hardWareService.getMemoryStatus();
  }
  
  @MessageMapping("/cpu")
  @SendTo("/topic/cpu")
  public String greeting6() throws Exception {
	 
	  log.info("������ ��Ʈ��ũ üũ: {}",hardWareService.getCpuStatus());
	  	  
	  return hardWareService.getCpuStatus();
  }
  
  @MessageMapping("/disk")
  @SendTo("/topic/disk")
  public  Map<String,List<String>> greeting7() throws Exception {
	 
	  log.info("������ ��Ʈ��ũ üũ: {}",hardWareService.getDiskStatus());
	  	  
	  return hardWareService.getDiskStatus();
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

}