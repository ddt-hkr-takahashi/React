package com.example.mapper.selfCreateMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.Entity.SpeedUsedCarDto;

@Mapper
public interface SpeedUsedCarMapper {

	List<SpeedUsedCarDto> getAnalyzedSalesData();

	List<Map<String, Object>> getAnalyzedSalesDataMapper();
}