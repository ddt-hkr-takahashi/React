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

/**
 * 販売情報に関するAPIエンドポイントを提供するRestControllerクラス
 */
@RestController
@RequestMapping("/api/sales-information")
public class SalesInfoController {

	@Autowired
	private SalesInformationService SalesInformationService;

	/**
	 * 販売情報を検索する
	 * @param prefCode 都道府県コード (任意)
	 * @param branchCode 支店コード (任意)
	 * @param makerCode メーカーコード (任意)
	 * @param typeCode 種類コード (任意)
	 * @param modelName モデル名 (任意)
	 * @param page ページ番号
	 * @param size 1ページあたりの表示件数
	 * @return 検索結果と総件数を含むMap
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

		// サービス層で販売情報を検索
		SearchResult searchResult = SalesInformationService.refineSearch(prefCode, branchCode, makerCode, typeCode,
				modelName, page,
				size);

		// レスポンスの形式を整形
		Map<String, Object> response = new HashMap<>();
		response.put("resultList", searchResult.getResultList());
		response.put("resultCountMessage", searchResult.getResultMessage());
		return response;
	}

	/**
	 * 販売情報を追加する
	 * @param requestBody 追加する販売データのMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> addFunction(
			@RequestBody Map<String, Object> requestBody) {

		UsedCars insertData = new UsedCars();

		// データ型に応じて安全に変換し、エンティティに設定
		insertData.setBranchCode(getIntegerFromObject(requestBody.get("branchCode")));
		insertData.setModelCode(requestBody.get("modelCode").toString());
		insertData.setOwnerCode(getIntegerFromObject(requestBody.get("ownerCode")));
		insertData.setSalesAmount(getIntegerFromObject(requestBody.get("salesAmount")));

		// サービス層で追加処理を実行
		String returnMessage = SalesInformationService.addData(insertData);
		// 処理結果に応じてレスポンスを返す
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "販売情報を追加しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 選択された販売情報を販売済みにする
	 * @param requestBody 販売済みにする販売IDの配列を含むMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/sales")
	public ResponseEntity<Map<String, String>> salesFunction(
			@RequestBody Map<String, Integer[]> requestBody) {

		// リクエストボディから販売IDの配列を取得
		Integer[] carIds = requestBody.get("carIds");

		// サービス層で販売済み処理を実行
		String returnMessage = SalesInformationService.salesData(carIds);
		// 処理結果に応じてレスポンスを返す
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "販売情報を販売済みにしました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 販売情報を編集する
	 * @param requestBody 編集する販売データを含むMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/edit")
	public ResponseEntity<Map<String, String>> editFunction(
			@RequestBody Map<String, Object> requestBody) {

		UsedCars editData = new UsedCars();

		// データ型に応じて安全に変換し、エンティティに設定
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

		// サービス層で編集処理を実行
		String returnMessage = SalesInformationService.editData(editData);
		// 処理結果に応じてレスポンスを返す
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "販売情報を編集しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 選択された販売情報を削除する
	 * @param requestBody 削除する販売IDの配列を含むMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/delete")
	public ResponseEntity<Map<String, String>> deleteFunction(
			@RequestBody Map<String, int[]> requestBody) {

		// リクエストボディから販売IDの配列を取得
		int[] carIds = requestBody.get("carIds");

		// サービス層で削除処理を実行
		String errorMessage = SalesInformationService.deleteData(carIds);
		// 処理結果に応じてレスポンスを返す
		if (errorMessage == null) {
			return new ResponseEntity<>(Map.of("message", "販売情報を削除しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", errorMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * ObjectをIntegerに安全に変換するヘルパーメソッド
	 * @param obj 変換対象のObject
	 * @return 変換後のInteger
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