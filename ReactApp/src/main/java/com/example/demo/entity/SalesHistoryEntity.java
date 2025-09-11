package com.example.demo.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 販売履歴機能に対応するentityクラス
 */

import lombok.Data;
@EntityScan
@Data
public class SalesHistoryEntity {

	// 一覧表示用
	private String prefName;
	private String storeName;
	private String branchName;
	private String branchCode;
	private String parchaseDate;
	private String PD;
    private String salesAmount; 
	private String makerName;
	private String typeName;
	private String modelName;
	private String modelCode;

	// グラフ表示用
	private int numberOfCars;

}
