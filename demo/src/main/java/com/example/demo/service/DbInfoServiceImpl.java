package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.dto.DbInfoDTO;
import com.example.demo.repository.DbInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DbInfoServiceImpl implements DbInfoService{
	
	private final DbInfoRepository repository;

	@Override
	public DbInfoDTO checkDbInfo() {
		// TODO Auto-generated method stub
		return repository.checkDbStatus();
	}

}
