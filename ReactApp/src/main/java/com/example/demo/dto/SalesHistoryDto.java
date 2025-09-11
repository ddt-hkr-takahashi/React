package com.example.demo.dto;

import lombok.Data;

/**
 * 販売履歴機能に対応するDTOクラス
 */
@Data
public class SalesHistoryDto {

	// 一覧表示用
	private String prefName;
	private String storeName;
	private String branchName;
	private String branchCode;
	private String parchaseDate;
	private String PD;
	private int salesAmount;
	private String makerName;
	private String typeName;
	private String modelName;
	private String modelCode;

	// グラフ表示用
	private int numberOfCars;

}
