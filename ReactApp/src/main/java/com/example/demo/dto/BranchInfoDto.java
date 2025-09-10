package com.example.demo.dto;

import lombok.Data;

/**
 * 複数のテーブルの結合結果を保持するエンティティ
 */
@Data
public class BranchInfoDto {

	private String branchCode;
	private String prefCode;
	private String storeCode;
	private String branchName;
	private String prefName;
	private String storeName;
}
