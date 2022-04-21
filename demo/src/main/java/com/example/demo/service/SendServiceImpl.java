package com.example.demo.service;
 
import java.util.List; 

import org.springframework.stereotype.Service;

import com.example.demo.dto.RecvDTO;
import com.example.demo.entity.Send;
import com.example.demo.repository.SendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendServiceImpl implements SendService{
	
	private final SendRepository repository;
	
	public List<RecvDTO> find() {
		
		List<Send>target = repository.findAll();
		
		return RecvDTO.entityListToDtoList2(target);
	}

}
