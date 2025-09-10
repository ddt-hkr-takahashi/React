package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SearchResult;
import com.example.demo.service.SalesInformationService;
import com.example.model.UsedCars; // UsedCarsをインポート

@RestController
@RequestMapping("/api/sales-information")
public class SalesInfoController {

	@Autowired
	private SalesInformationService SalesInformationService;

	/**
	 * 販売情報を検索します。
	 */
	@GetMapping("/search-result")
	public Map<String, Object> searchCars(
			@RequestParam(required = false) String prefCode,
			@RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String makerCode,
			@RequestParam(required = false) String typeCode,
			@RequestParam(required = false) String modelName,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int size) {
		
		SearchResult searchResult = SalesInformationService.refineSearch(prefCode, branchCode, makerCode, typeCode,
				modelName, page,
				size);

		Map<String, Object> response = new HashMap<>();
		response.put("resultList", searchResult.getSalesResultList());
		response.put("resultCountMessage", searchResult.getResultCountMessage());
		return response;
	}

	/**
	 * 販売情報を追加します。
	 */
	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> addFunction(
			@RequestBody Map<String, Object> requestBody) {

		UsedCars insertData = new UsedCars();

		// データ型に応じて安全に変換
		insertData.setBranchCode(getIntegerFromObject(requestBody.get("branchCode")));
		insertData.setModelCode(requestBody.get("modelCode").toString());
		insertData.setOwnerCode(getIntegerFromObject(requestBody.get("ownerCode")));
		insertData.setSalesAmount(getIntegerFromObject(requestBody.get("salesAmount")));

		String returnMessage = SalesInformationService.addData(insertData);
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "販売情報を追加しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 選択された販売情報を販売済みにします。
	 */
	@PostMapping("/sales")
	public ResponseEntity<Map<String, String>> salesFunction(
			@RequestBody Map<String, Integer[]> requestBody) {
		
		Integer[] carIds = requestBody.get("carIds");
		
		String returnMessage = SalesInformationService.salesData(carIds);
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "販売情報を販売済みにしました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 販売情報を編集します。
	 */
	@PostMapping("/edit")
	public ResponseEntity<Map<String, String>> editFunction(
	        @RequestBody Map<String, Object> requestBody) {

	    UsedCars editData = new UsedCars();

	    // データ型に応じて安全に変換
	    editData.setCarId(getIntegerFromObject(requestBody.get("carId")));
	    editData.setBranchCode(getIntegerFromObject(requestBody.get("branchCode")));
	    editData.setModelCode(requestBody.get("modelCode").toString());
	    
	    Object ownerCodeObject = requestBody.get("ownerCode");
	    if (ownerCodeObject == null || (ownerCodeObject instanceof String && ((String) ownerCodeObject).isEmpty())) {
	        editData.setOwnerCode(null);
	    } else {
	        editData.setOwnerCode(getIntegerFromObject(ownerCodeObject));
	    }
	    
	    editData.setSalesAmount(getIntegerFromObject(requestBody.get("salesAmount")));

	    String returnMessage = SalesInformationService.editData(editData);
	    if (returnMessage == null) {
	        return new ResponseEntity<>(Map.of("message", "販売情報を編集しました。"), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
	    }
	}

	/**
	 * 選択された販売情報を削除します。
	 */
	@PostMapping("/delete")
	public ResponseEntity<Map<String, String>> deleteFunction(
			@RequestBody Map<String, int[]> requestBody) {
				
		int[] carIds = requestBody.get("carIds");
		
		String errorMessage = SalesInformationService.deleteData(carIds);
		if (errorMessage == null) {
			return new ResponseEntity<>(Map.of("message", "販売情報を削除しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", errorMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * ObjectをIntegerに安全に変換するヘルパーメソッド
	 */
	private Integer getIntegerFromObject(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof Integer) {
			return (Integer) obj;
		}
		if (obj instanceof String) {
			try {
				return Integer.parseInt((String) obj);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid number format for conversion: " + obj, e);
			}
		}
		throw new IllegalArgumentException("Unsupported type for conversion to Integer: " + obj.getClass().getName());
	}
}