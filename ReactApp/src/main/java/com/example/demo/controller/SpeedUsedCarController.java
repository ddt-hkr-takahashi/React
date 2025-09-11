package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.SpeedUsedCarService;

@Controller
@RequestMapping("/api")
public class SpeedUsedCarController {

	private final SpeedUsedCarService speedUsedCarService;

	public SpeedUsedCarController(SpeedUsedCarService speedUsedCarService) {
		this.speedUsedCarService = speedUsedCarService;
	}

	@GetMapping("/sales-history/download-report")
	public ResponseEntity<String> downloadReport() {

		String csvContent = speedUsedCarService.generateCsvReport();

		// レスポンスヘッダーの設定

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType("text/csv; charset=Shift_JIS"));

		headers.setContentDispositionFormData("attachment", "used_car_report.csv");

		return new ResponseEntity<>(csvContent, headers, org.springframework.http.HttpStatus.OK);

	}
}