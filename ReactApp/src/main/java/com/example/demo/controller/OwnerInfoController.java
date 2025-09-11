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
import com.example.demo.service.OwnerInformationService;

@RestController
@RequestMapping("/api/owners-information")
public class OwnerInfoController {

	@Autowired
	private OwnerInformationService ownerInformationService;

	/**
	 * 所有者情報を検索します。
	 * @param ownerName 所有者名 (任意)
	 * @param gender 性別 (任意)
	 * @param minAge 年齢下限 (任意)
	 * @param maxAge 年齢上限 (任意)
	 * @return 検索結果と総件数を含むMap
	 */
	@GetMapping("/search-result")
	public Map<String, Object> searchOwners(
			@RequestParam(required = false) String ownerName,
			@RequestParam(required = false) String gender,
			@RequestParam(required = false) String minAge,
			@RequestParam(required = false) String maxAge,
			// ページ番号と1ページあたりの表示件数を追加
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int size) {

		SearchResult searchResult = ownerInformationService.refineSearch(ownerName, gender, minAge, maxAge, page, size);
		// フロントエンドの期待する形式に変換
		Map<String, Object> response = new HashMap<>();
		response.put("resultList", searchResult.getResultList());
		response.put("resultCountMessage", searchResult.getResultMessage());

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
		String ownerName = (String) requestBody.get("ownerName");
		String gender = (String) requestBody.get("gender");
		String age = requestBody.get("age").toString();

		String returnMessage = ownerInformationService.addData(ownerName, gender, age);
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "所有者情報を追加しました。"), HttpStatus.OK);
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
		String ownerName = (String) requestBody.get("ownerName");
		String gender = (String) requestBody.get("gender");
		String age = requestBody.get("age").toString();
		String ownerCode = requestBody.get("ownerCode").toString();

		String returnMessage = ownerInformationService.editData(ownerName, gender, age, ownerCode);
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "所有者情報を編集しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * データ削除機能
	 * @param requestBody 削除する所有者コードの配列を含むMap
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/delete")
	public ResponseEntity<Map<String, String>> deleteFunction(
			@RequestBody Map<String, int[]> requestBody) {
		int[] ownerCodes = requestBody.get("ownerCodes");
		String errorMessage = ownerInformationService.deleteData(ownerCodes);
		if (errorMessage == null) {
			return new ResponseEntity<>(Map.of("message", "所有者情報を削除しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", errorMessage), HttpStatus.BAD_REQUEST);
		}
	}
}