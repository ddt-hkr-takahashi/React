package com.example.mapper.selfCreateMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.Entity.SalesInfoEntity;

@Mapper
public interface SalesInfoMapper {

	List<Map<String, Object>> searchUsedCars(
			@Param("prefCode") String prefCode,
			@Param("branchCode") String branchCode,
			@Param("makerCode") String makerCode,
			@Param("typeCode") String typeCode,
			@Param("modelName") String modelName,
			@Param("offset") int offset,
			@Param("limit") int limit);
	
	SalesInfoEntity findById(
			@Param("carId") int carId);

	int countUsedCars(
			@Param("prefCode") String prefCode,
			@Param("branchCode") String branchCode,
			@Param("makerCode") String makerCode,
			@Param("typeCode") String typeCode,
			@Param("modelName") String modelName);
}
