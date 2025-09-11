package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SpeedUsedCarDto;
import com.example.mapper.SpeedUsedCarMapper;

@Service
public class SpeedUsedCarService {

	private final SpeedUsedCarMapper speedUsedCarMapper;

	public SpeedUsedCarService(SpeedUsedCarMapper speedUsedCarMapper) {
		this.speedUsedCarMapper = speedUsedCarMapper;
	}

	public String generateCsvReport() {
		// データベースに1回だけ接続し、全データを取得
		List<SpeedUsedCarDto> resultList = speedUsedCarMapper.getAnalyzedSalesData();

		// saleStatusに基づいて3つのリストに分割
		List<SpeedUsedCarDto> highPricedSales = resultList.stream()
				.filter(dto -> dto.getSaleStatus() == 1)
				.collect(Collectors.toList());

		List<SpeedUsedCarDto> normalPricedSales = resultList.stream()
				.filter(dto -> dto.getSaleStatus() == 0)
				.collect(Collectors.toList());

		List<SpeedUsedCarDto> lowPricedSales = resultList.stream().filter(dto -> dto.getSaleStatus() == -1)
				.collect(Collectors.toList());

		// CSVヘッダー
		String csvHeader = "都道府県,販売店,支店名,販売日,販売額,定価,メーカ,タイプ,車種\n";

		// CSVコンテンツの構築
		StringBuilder csvContent = new StringBuilder();

		csvContent.append("--- 定価以上で販売ここから ---\n");
		csvContent.append(csvHeader);
		for (SpeedUsedCarDto dto : highPricedSales) {
			csvContent.append(dto.getPrefName()).append(",")
					.append(dto.getStoreName()).append(",")
					.append(dto.getBranchName()).append(",")
					.append(dto.getPD()).append(",")
					.append(dto.getSalesAmount()).append(",")
					.append(dto.getListPrice()).append(",")
					.append(dto.getMakerName()).append(",")
					.append(dto.getTypeName()).append(",")
					.append(dto.getModelName()).append("\n");
		}
		csvContent.append("--- 定価以上で販売ここまで ---\n\n");

		csvContent.append("--- 定価程度で販売ここから ---\n");
		csvContent.append(csvHeader);
		for (SpeedUsedCarDto dto : normalPricedSales) {
			csvContent.append(dto.getPrefName()).append(",")
					.append(dto.getStoreName()).append(",")
					.append(dto.getBranchName()).append(",")
					.append(dto.getPD()).append(",")
					.append(dto.getSalesAmount()).append(",")
					.append(dto.getListPrice()).append(",")
					.append(dto.getMakerName()).append(",")
					.append(dto.getTypeName()).append(",")
					.append(dto.getModelName()).append("\n");
		}
		csvContent.append("--- 定価程度で販売ここまで ---\n\n");

		csvContent.append("--- 安い価格販売ここから ---\n");
		csvContent.append(csvHeader);
		for (SpeedUsedCarDto dto : lowPricedSales) {
			csvContent.append(dto.getPrefName()).append(",")
					.append(dto.getStoreName()).append(",")
					.append(dto.getBranchName()).append(",")
					.append(dto.getPD()).append(",")
					.append(dto.getSalesAmount()).append(",")
					.append(dto.getListPrice()).append(",")
					.append(dto.getMakerName()).append(",")
					.append(dto.getTypeName()).append(",")
					.append(dto.getModelName()).append("\n");
		}
		csvContent.append("--- 安い価格販売ここまで ---\n");

		return csvContent.toString();
	}
}