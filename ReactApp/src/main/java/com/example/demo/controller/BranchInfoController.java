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
import com.example.demo.service.BranchInformationService;

/**
 * 店舗情報に関するAPIエンドポイントを提供するRestControllerクラス
 */
@RestController
@RequestMapping("/api/branch-information")
public class BranchInfoController {

	@Autowired
	private BranchInformationService branchInformationService;

	/**
	 * 店舗情報を検索する
	 * @param prefCode 都道府県コード (任意)
	 * @param storeCode 店舗コード (任意)
	 * @param page ページ番号
	 * @param size 1ページあたりの表示件数
	 * @return 検索結果と総件数を含むMap
	 */
	@GetMapping("/search-result")
	public Map<String, Object> searchBranches(
			@RequestParam(required = false) String prefCode,
			@RequestParam(required = false) String storeCode,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int size) {
		// サービス層で店舗情報を検索
		SearchResult searchResult = branchInformationService.refineSearch(prefCode, storeCode, page,
				size);

		// レスポンスの形式を整形
		Map<String, Object> response = new HashMap<>();
		response.put("resultList", searchResult.getResultList());
		response.put("resultCountMessage", searchResult.getResultMessage());

		return response;
	}

	/**
	 * 店舗情報を追加する
	 * @param requestBody 追加する店舗データのMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> addFunction(
			@RequestBody Map<String, Object> requestBody) {
		// リクエストボディからデータ抽出
		String prefCode = (String) requestBody.get("prefCode");
		String storeCode = (String) requestBody.get("storeCode");
		String branchName = (String) requestBody.get("branchName");

		// サービス層で追加処理を実行
		String returnMessage = branchInformationService.addData(prefCode, storeCode, branchName);
		// 処理結果に応じてレスポンスを返す
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "店舗情報を追加しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 店舗情報を編集する
	 * @param requestBody 編集する店舗データを含むMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/edit")
	public ResponseEntity<Map<String, String>> editFunction(
			@RequestBody Map<String, Object> requestBody) {
		// リクエストボディからデータ抽出
		String prefCode = (String) requestBody.get("prefCode");
		String storeCode = (String) requestBody.get("storeCode");
		String branchName = (String) requestBody.get("branchName");
		Integer branchCode = (Integer) requestBody.get("branchCode");

		// サービス層で編集処理を実行
		String returnMessage = branchInformationService.editData(prefCode, storeCode, branchName, branchCode);
		// 処理結果に応じてレスポンスを返す
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "店舗情報を編集しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 店舗情報を削除する
	 * @param requestBody 削除する支店コードの配列を含むMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/delete")
	public ResponseEntity<Map<String, String>> deleteFunction(
			@RequestBody Map<String, int[]> requestBody) {
		// リクエストボディから削除対象の支店コード配列を取得
		int[] branchCodes = requestBody.get("branchCodes");
		// サービス層で削除処理を実行
		String errorMessage = branchInformationService.deleteData(branchCodes);
		// 処理結果に応じてレスポンスを返す
		if (errorMessage == null) {
			return new ResponseEntity<>(Map.of("message", "店舗情報を削除しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", errorMessage), HttpStatus.BAD_REQUEST);
		}
	}
}