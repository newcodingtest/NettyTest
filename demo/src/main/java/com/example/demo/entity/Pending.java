package com.example.demo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class Pending {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column
	private String data;
	
	@Column
	private String reason;
	
	@Column
	private LocalDateTime regdate;
}
