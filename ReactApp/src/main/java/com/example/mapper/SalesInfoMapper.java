package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

import com.example.demo.dto.SalesInfoDto;
import com.example.model.BranchesList;

@Mapper
public interface SalesInfoMapper {

	List<SalesInfoDto> searchUsedCars (
			@Param("prefCode") String prefCode,
			@Param("branchCode") String branchCode,
			@Param("makerCode") String makerCode,
			@Param("typeCode") String typeCode,
			@Param("modelName") String modelName,
			@Param("offset") int offset,
			@Param("limit") int limit) throws PersistenceException;

	int countUsedCars(
			@Param("prefCode") String prefCode,
			@Param("branchCode") String branchCode,
			@Param("makerCode") String makerCode,
			@Param("typeCode") String typeCode,
			@Param("modelName") String modelName) throws PersistenceException;
	
	List<SalesInfoDto> selectModelsWithCombinedName() throws PersistenceException;
	
	List<BranchesList> findByPrefCode(@Param("prefCode") String prefCode)throws PersistenceException;
}