package com.example.demo.service;

import java.util.List; 

import org.springframework.stereotype.Service;

import com.example.demo.dto.PendigDTO;
import com.example.demo.entity.Pending;
import com.example.demo.repository.PendingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PendingServiceImpl implements PendingService{
	
	private final PendingRepository repository;
	
	public List<PendigDTO> find() {
		
		List<Pending>target = repository.findAll();
		
		return PendigDTO.entityListToDtoList(target);
	}

}
