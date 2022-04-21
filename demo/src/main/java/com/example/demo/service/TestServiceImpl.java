package com.example.demo.service;

import java.util.List; 

import org.springframework.stereotype.Service;

import com.example.demo.dto.RecvDTO;
import com.example.demo.entity.Message;
import com.example.demo.repository.TestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService{
	
	private final TestRepository repository;
	
	public List<RecvDTO> find() {
		
		List<Message>target = repository.findAll();
		
		return RecvDTO.entityListToDtoList(target);
	}

}
