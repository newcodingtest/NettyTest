package com.example.demo.service;

import java.util.List; 

import org.springframework.stereotype.Service;

import com.example.demo.dto.RecvDTO;
import com.example.demo.entity.Recv;
import com.example.demo.repository.RecvRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecvServiceImpl implements RecvService{
	
	private final RecvRepository repository;
	
	public List<RecvDTO> find() {
		
		List<Recv>target = repository.findAll();
		
		return RecvDTO.entityListToDtoList1(target);
	}

}
