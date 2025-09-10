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

@RestController
@RequestMapping("/api/branch-information")
public class BranchInfoController {

	@Autowired
	private BranchInformationService branchInformationService;

	/**
	 * 店舗情報を検索します。
	 * @param prefCode 都道府県コード (任意)
	 * @param storeCode 店舗コード (任意)
	 * @return 検索結果と総件数を含むMap
	 */
	@GetMapping("/search-result")
	public Map<String, Object> searchBranches(
			@RequestParam(required = false) String prefCode,
			@RequestParam(required = false) String storeCode,
			// ページ番号と1ページあたりの表示件数を追加
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int size) {
		SearchResult searchResult = branchInformationService.refineSearch(prefCode, storeCode, page,
				size);

		// フロントエンドの期待する形式に変換
		Map<String, Object> response = new HashMap<>();
		response.put("resultList", searchResult.getPopularResultList());
		response.put("resultCountMessage", searchResult.getResultCountMessage());

		return response;
	}

	/**
	 * データ追加機能
	 * @param requestBody 追加するデータのMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> addFunction(
			@RequestBody Map<String, Object> requestBody) {
		String prefCode = (String) requestBody.get("prefCode");
		String storeCode = (String) requestBody.get("storeCode");
		String branchName = (String) requestBody.get("branchName");

		String returnMessage = branchInformationService.addData(prefCode, storeCode, branchName);
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "店舗情報を追加しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * データ編集機能
	 * @param requestBody 編集するデータを含むMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/edit")
	public ResponseEntity<Map<String, String>> editFunction(
			@RequestBody Map<String, Object> requestBody) {
		String prefCode = (String) requestBody.get("prefCode");
		String storeCode = (String) requestBody.get("storeCode");
		String branchName = (String) requestBody.get("branchName");
		Integer branchCode = (Integer) requestBody.get("branchCode");

		String returnMessage = branchInformationService.editData(prefCode, storeCode, branchName, branchCode);
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "店舗情報を編集しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * データ削除機能
	 * @param requestBody 削除する支店コードの配列を含むMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/delete")
	public ResponseEntity<Map<String, String>> deleteFunction(
			@RequestBody Map<String, int[]> requestBody) {
		int[] branchCodes = requestBody.get("branchCodes");
		String errorMessage = branchInformationService.deleteData(branchCodes);
		if (errorMessage == null) {
			return new ResponseEntity<>(Map.of("message", "店舗情報を削除しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", errorMessage), HttpStatus.BAD_REQUEST);
		}
	}
}