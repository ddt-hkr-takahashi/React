package com.example.demo.dto;

import lombok.Data;

/**
 * owners_listテーブルに対応するエンティティクラス
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