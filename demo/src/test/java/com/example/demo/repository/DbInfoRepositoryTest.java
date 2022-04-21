package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.DbInfoDTO;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
class DbInfoRepositoryTest {

	@Autowired
	private DbInfoRepository repository;
	
	@Test
	void test() {
		DbInfoDTO result = repository.checkDbStatus();
			
		log.info(result.toString());
		
	}

}
