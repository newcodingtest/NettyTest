package com.example.demo.repository;


import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.DbInfoDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DbInfoRepository {

	private final EntityManager entityManager;
	
	public DbInfoDTO checkDbStatus(){
		String sql = "select\r\n"
				+ "    table_schema as \"dbName\",\r\n"
				+ "    sum(data_length + index_length) / 1024 / 1024 as\"activesize\",\r\n"
				+ "    sum(data_free) / 1024 / 1024 as\"fullsize\"\r\n"
				+ "from\r\n"
				+ "    information_schema.TABLES\r\n"
				+ "    where table_schema ='test'\r\n"
				+ "group by\r\n"
				+ "    table_schema";
		
		Query nativeQuery = entityManager.createNativeQuery(sql);
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		
		return jpaResultMapper.uniqueResult(nativeQuery, DbInfoDTO.class);
	}
}
