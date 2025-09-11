package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.SpeedUsedCarDto;

@Mapper
public interface SpeedUsedCarMapper {
    List<SpeedUsedCarDto> getAnalyzedSalesData();
}