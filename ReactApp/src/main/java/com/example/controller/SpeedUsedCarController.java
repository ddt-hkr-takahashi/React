package com.example.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SpeedUsedCarDto;
import com.example.service.SpeedUsedCarService;

@RestController
@RequestMapping("/api/sales-history")
public class SpeedUsedCarController {

    private final SpeedUsedCarService speedUsedCarService;

    public SpeedUsedCarController(SpeedUsedCarService speedUsedCarService) {
        this.speedUsedCarService = speedUsedCarService;
    }

    @GetMapping("/speed-test")
    public ResponseEntity<String> downloadReport() {
        // 各分類のデータを取得
        List<SpeedUsedCarDto> highPricedSales = speedUsedCarService.getHighPricedSales();
        List<SpeedUsedCarDto> normalPricedSales = speedUsedCarService.getNormalPricedSales();
        List<SpeedUsedCarDto> lowPricedSales = speedUsedCarService.getLowPricedSales();

        // CSVヘッダー
        String csvHeader = "都道府県,販売店,支店名,販売日,販売額,定価,メーカ,タイプ,車種\n";
        
        // CSVコンテンツの構築
        StringBuilder csvContent = new StringBuilder();

        csvContent.append("--- 定価以上で販売ここから ---\n");
        csvContent.append(csvHeader);
        for (SpeedUsedCarDto dto : highPricedSales) {
            csvContent.append(dto.getPrefecture()).append(",")
                      .append(dto.getStoreName()).append(",")
                      .append(dto.getBranchName()).append(",")
                      .append(dateFormat.format(dto.getSaleDate())).append(",")
                      .append(dto.getSellingPrice()).append(",")
                      .append(dto.getListPrice()).append(",")
                      .append(dto.getMaker()).append(",")
                      .append(dto.getCarType()).append(",")
                      .append(dto.getCarName()).append("\n");
        }
        csvContent.append("--- 定価以上で販売ここまで ---\n\n");

        csvContent.append("--- 定価程度で販売ここから ---\n");
        csvContent.append(csvHeader);
        for (SpeedUsedCarDto dto : normalPricedSales) {
            csvContent.append(dto.getPrefecture()).append(",")
                      .append(dto.getStoreName()).append(",")
                      .append(dto.getBranchName()).append(",")
                      .append(dateFormat.format(dto.getSaleDate())).append(",")
                      .append(dto.getSellingPrice()).append(",")
                      .append(dto.getListPrice()).append(",")
                      .append(dto.getMaker()).append(",")
                      .append(dto.getCarType()).append(",")
                      .append(dto.getCarName()).append("\n");
        }
        csvContent.append("--- 定価程度で販売ここまで ---\n\n");
        
        csvContent.append("--- 安い価格販売ここから ---\n");
        csvContent.append(csvHeader);
        for (SpeedUsedCarDto dto : lowPricedSales) {
            csvContent.append(dto.getPrefecture()).append(",")
                      .append(dto.getStoreName()).append(",")
                      .append(dto.getBranchName()).append(",")
                      .append(dateFormat.format(dto.getSaleDate())).append(",")
                      .append(dto.getSellingPrice()).append(",")
                      .append(dto.getListPrice()).append(",")
                      .append(dto.getMaker()).append(",")
                      .append(dto.getCarType()).append(",")
                      .append(dto.getCarName()).append("\n");
        }
        csvContent.append("--- 安い価格販売ここまで ---\n");

        // レスポンスヘッダーの設定
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv; charset=Shift_JIS"));
        headers.setContentDispositionFormData("attachment", "used_car_report.csv");

        return new ResponseEntity<>(csvContent.toString(), headers, org.springframework.http.HttpStatus.OK);
    }
}