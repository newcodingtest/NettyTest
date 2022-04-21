package com.example.demo.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Message;
import com.example.demo.entity.Pending;
import com.example.demo.entity.Recv;
import com.example.demo.entity.Send;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PendigDTO {
	
	private Long id;
	
	private String data;
	
	private String regdate;
	
	private String reason;
	
	
	public static PendigDTO entityToDTO(Pending entity) {
		return PendigDTO.builder()
				.id(entity.getIdx())
				.data(entity.getData())
				.regdate(entity.getRegdate().toString())
				.reason(entity.getReason())
				.build();
	}

	
	public static List<PendigDTO> entityListToDtoList(List<Pending> list){
		
		return list.stream()
					 .map(PendigDTO::entityToDTO)
					 .collect(Collectors.toList());
	}

}
