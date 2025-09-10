package com.example.demo.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.Data;

/**
 * owners_listテーブルに対応するエンティティクラス
 */
@EntityScan
@Data
public class OwnerInfoEntity {

	private String ownerCode;
	private String ownerName;
	private String sex;
	private int age;
	
	//売却台数
	private int salesCount;
}