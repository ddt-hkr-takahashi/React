package com.example.service;

import com.example.demo.dto.SpeedUsedCarDto;
import com.example.mapper.SpeedUsedCarMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpeedUsedCarService {

	@Autowired
	private SpeedUsedCarMapper speedUsedCarMapper;

	private List<SpeedUsedCarDto> getAllSalesData() {
		// データベースへの接続はこのメソッド呼び出しで1回のみ
		return speedUsedCarMapper.getAnalyzedSalesData();
	}
	
	private List<Map<>>

}