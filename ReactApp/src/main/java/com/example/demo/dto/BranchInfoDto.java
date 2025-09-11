package com.example.demo.dto;

import lombok.Data;

/**
 * 店舗情報管理機能に対応するDTOクラス
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
