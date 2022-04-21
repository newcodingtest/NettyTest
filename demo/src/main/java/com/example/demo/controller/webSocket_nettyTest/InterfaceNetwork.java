package com.example.demo.controller.webSocket_nettyTest;

import java.util.concurrent.ConcurrentHashMap;

//ΩÃ±€≈Ê 
public enum InterfaceNetwork {
	INSTANCE;
	
	private final static ConcurrentHashMap<String, String>status = new ConcurrentHashMap<String, String>();
	
	private InterfaceNetwork() {
	}
	
	public synchronized static InterfaceNetwork getInstance() {
		return INSTANCE;
	}
	
	public ConcurrentHashMap<String, String> getStatus(){
		return this.status;
	}
	
	public void changeNetworkTrue(int port) {
		  if(port==8081) {
			  status.put("8081", "T");
		  }else if(port==8082) {
			  status.put("8082", "T");
		  }else if(port==8083) {
			  status.put("8083", "T");
		  }
	  }
	  
	public void changeNetworkFalse(int port) {
		  if(port==8081) {
			  status.put("8081", "F");
		  }else if(port==8082) {
			  status.put("8082", "F");
		  }else if(port==8083) {
			  status.put("8083", "F");
		  }
	  }
}

