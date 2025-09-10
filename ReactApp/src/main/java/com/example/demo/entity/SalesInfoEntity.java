package com.example.demo.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

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
