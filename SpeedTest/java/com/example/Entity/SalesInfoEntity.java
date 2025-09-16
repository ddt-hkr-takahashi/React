package com.example.Entity;

import lombok.Data;

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
