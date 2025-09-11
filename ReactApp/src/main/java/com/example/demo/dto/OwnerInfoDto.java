package com.example.demo.dto;

import lombok.Data;

/**
 * 元所有者管理機能に対応するDTOクラス
 */
@Data
public class OwnerInfoDto {

	private String ownerCode;
	private String ownerName;
	private String gender;
	private int age;
	
	//売却台数
	private int salesCount;
}