package com.example.Entity;

import lombok.Data;

/**
 * owners_listテーブルに対応するエンティティクラス
 */
@Data
public class OwnerInfoEntity {

	private String ownerCode;
	private String ownerName;
	private String sex;
	private int age;
	
	//売却台数
	private int salesCount;
}