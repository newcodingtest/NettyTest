package com.example.demo.dto;

import java.util.List; 
import java.util.stream.Collectors;

import com.example.demo.entity.Message;
import com.example.demo.entity.Recv;
import com.example.demo.entity.Send;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecvDTO {
	
	private Long id;
	
	private String data;
	
	private String regdate;
	
	private String flag;
	
	
	public static RecvDTO entityToDTO(Message entity) {
		return RecvDTO.builder()
				.id(entity.getMid())
				.data(entity.getData())
				.build();
	}
	public static RecvDTO entityToDTO1(Recv entity) {
		return RecvDTO.builder()
				.id(entity.getIdx())
				.data(entity.getData())
				.regdate(entity.getRegdate().toString())
				.flag(entity.getFlag())
				.build();
	}
	public static RecvDTO entityToDTO2(Send entity) {
		return RecvDTO.builder()
				.id(entity.getMid())
				.data(entity.getData())
				.build();
	}
	
	public static List<RecvDTO> entityListToDtoList(List<Message> list){
		
		return list.stream()
					 .map(RecvDTO::entityToDTO)
					 .collect(Collectors.toList());
	}
	
public static List<RecvDTO> entityListToDtoList1(List<Recv> list){
		
		return list.stream()
					 .map(RecvDTO::entityToDTO1)
					 .collect(Collectors.toList());
	}

public static List<RecvDTO> entityListToDtoList2(List<Send> list){
	
	return list.stream()
				 .map(RecvDTO::entityToDTO2)
				 .collect(Collectors.toList());
}

}
