package com.example.demo.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 販売情報管理機能に対応するentityクラス
 */

import lombok.Data;
@EntityScan
@Data
public class SalesInfoEntity {
	private String carId;
	private String prefName;
	private String branchName;
	private String makerName;
	private String typeName;
	private String modelName;
	private String ownerName;
	private int salesAmount;
}
