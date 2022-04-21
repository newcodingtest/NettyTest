package com.example.demo.dto;

import java.math.BigDecimal; 

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DbInfoDTO {

	private String dbName;
	private BigDecimal activeSize;
	private BigDecimal fullSize;
	
}
