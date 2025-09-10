package com.example.demo.dto;

import lombok.Data;

@Data
public class SalesInfoDto {
	private String carId;
	private String prefName;
	private String branchName;
	private String makerName;
	private String typeName;
	private String modelName;
	private String ownerName;
	private String prefCode;
	private String branchCode;
	private String makerCode;
	private String typeCode;
	private String modelCode;
	private String ownerCode;
    private String salesAmount; 
	private String combinedName;
}