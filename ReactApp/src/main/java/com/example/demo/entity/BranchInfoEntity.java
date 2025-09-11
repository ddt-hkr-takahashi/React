package com.example.demo.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.Data;

/**
 * 店舗情報管理機能に対応するentityクラス
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
