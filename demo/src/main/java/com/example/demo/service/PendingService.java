package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PendigDTO;
import com.example.demo.dto.RecvDTO;

public interface PendingService {
	
	List<PendigDTO> find();
	
	
}
