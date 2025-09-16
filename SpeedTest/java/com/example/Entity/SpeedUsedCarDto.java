package com.example.Entity;

import lombok.Data;

@Data
public class SpeedUsedCarDto {

	private String prefName;
	private String storeName;
	private String branchName;
	private String makerName;
	private String TypeName;
	private String modelName;
	private Integer modelCode;
	private Integer branchCode;
	private String PD;
	private Integer salesAmount;
	private Integer listPrice;
	private Integer saleStatus;
}