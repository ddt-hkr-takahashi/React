package com.example.demo.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.Data;

/**
 * 複数のテーブルの結合結果を保持するエンティティ
 */
@EntityScan
@Data
public class BranchInfoEntity {

	private String branchCode;
	private String prefCode;
	private String storeCode;
	private String branchName;
	private String prefName;
	private String storeName;
}
