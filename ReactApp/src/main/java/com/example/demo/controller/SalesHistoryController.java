package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SearchResult;
import com.example.demo.service.SalesHistoryService;

/**
 * 販売履歴に関するAPIエンドポイントを提供するRestControllerクラス
 */
@RestController
@RequestMapping("/api/sales-history")
public class SalesHistoryController {

	@Autowired
	private SalesHistoryService SalesHistoryService;

	/**
	 * 販売履歴を検索する
	 * @param storeCode 店舗コード (任意)
	 * @param yearNumber 年 (任意)
	 * @param monthNumber 月 (任意)
	 * @param page ページ番号
	 * @param size 1ページあたりの表示件数
	 * @return 検索結果と総件数を含むMap
	 */
	@GetMapping("/search-result")
	public Map<String, Object> searchSalesHistory(
			@RequestParam(required = false) String storeCode,
			@RequestParam(required = false) String yearNumber,
			@RequestParam(required = false) String monthNumber,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int size) {
		// サービス層で販売履歴を検索
		SearchResult searchResult = SalesHistoryService.refineSearch(storeCode, yearNumber, monthNumber, page,
				size);

		// レスポンスの形式を整形
		Map<String, Object> response = new HashMap<>();
		response.put("resultList", searchResult.getResultList());
		response.put("resultCountMessage", searchResult.getResultMessage());
		return response;
	}

	/**
	 * グラフ表示用に集計検索を実行する
	 * @param storeCode 店舗コード (任意)
	 * @param year 年 (任意)
	 * @param month 月 (任意)
	 * @return 集計結果のリスト
	 */
	@GetMapping("/search-gragh")
	public List<Map<String, Object>> searchSalesHistoryGragh(
			@RequestParam(required = false) String storeCode,
			@RequestParam(required = false) String year,
			@RequestParam(required = false) String month) {
		// サービス層でグラフ用の集計検索を実行
		return SalesHistoryService.refineSearchGragh(storeCode, year, month);
	}

	/**
	 * CSVファイルをアップロードし、データをデータベースに挿入する
	 * @param file アップロードされたMultipartFile
	 * @return 実行結果メッセージを含むJSONレスポンス
	 */
	@PostMapping("/import-csv")
	public ResponseEntity<Map<String, String>> importCSV(@RequestParam("file") MultipartFile file) {
		// サービス層でCSVインポート処理を実行
		String returnMessage = SalesHistoryService.insertData(file);
		// 処理結果に応じてレスポンスを返す
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "CSVファイルのインポートが完了しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

	/**
			     * 検索結果を基にCSVファイルを作成し、ダウンロードさせる
			 * @param storeCode 店舗コード (任意)
			 * @param yearNumber 年 (任意)
			 * @param monthNumber 月 (任意)
			     * @return ダウンロード用のCSVファイルを含むResponseEntity
			     */

	@PostMapping("/export-csv")
	public ResponseEntity<Resource> createCSVAndDownload(@RequestParam(required = false) String storeCode,
			@RequestParam(required = false) String yearNumber,
			@RequestParam(required = false) String monthNumber) {

		// サービス層で検索を実行し、結果を取得
		List<Map<String, Object>> resultList = SalesHistoryService.refineSearchCSV(storeCode, yearNumber, monthNumber);
		String fileName = "sales_history.csv";
		Path filePath = Paths.get(fileName);

		try {
			// サーバーにCSVファイルを作成
			SalesHistoryService.createCSV(resultList, fileName);

			// 作成したファイルをリソースとして取得
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists() || resource.isReadable()) {
				// ダウンロード用のヘッダーを設定
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, "text/csv;charset=Shift_JIS");

				// レスポンスエンティティとしてファイルを返す
				return ResponseEntity.ok()
						.headers(headers)
						.body(resource);
			} else {
				throw new RuntimeException("CSVファイルが見つからないか、読み取れません。");
			}

		} catch (IOException | RuntimeException e) {
			// 例外発生時のエラーレスポンス
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}