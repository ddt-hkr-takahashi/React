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

@RestController
@RequestMapping("/api/sales-history")
public class SalesHistoryController {

	@Autowired
	private SalesHistoryService SalesHistoryService;

	/**
	 * 販売履歴を検索します。
	 */
	@GetMapping("/search-result")
	public Map<String, Object> searchSalesHistory(
			@RequestParam(required = false) String storeCode,
			@RequestParam(required = false) String yearNumber,
			@RequestParam(required = false) String monthNumber,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int size) {

		SearchResult searchResult = SalesHistoryService.refineSearch(storeCode, yearNumber, monthNumber, page,
				size);

		Map<String, Object> response = new HashMap<>();
		response.put("resultList", searchResult.getPopularResultList());
		response.put("resultCountMessage", searchResult.getResultCountMessage());
		return response;
	}

	/**
	 * グラフ表示用に集計検索を実行します。
	 */
	@GetMapping("/search-gragh")
	public List<Map<String, Object>> searchSalesHistoryGragh(
			@RequestParam(required = false) String storeCode,
			@RequestParam(required = false) String year,
			@RequestParam(required = false) String month) {

		return SalesHistoryService.refineSearchGragh(storeCode, year, month);
	}

	/**
	 * CSVファイルをアップロードし、データをデータベースに挿入します。
	 */
	@PostMapping("/import-csv")
	public ResponseEntity<Map<String, String>> importCSV(@RequestParam("file") MultipartFile file) {
		String returnMessage = SalesHistoryService.insertData(file);
		if (returnMessage == null) {
			return new ResponseEntity<>(Map.of("message", "CSVファイルのインポートが完了しました。"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("errorMessage", returnMessage), HttpStatus.BAD_REQUEST);
		}
	}

    /**
     * 検索結果を基にCSVファイルを作成し、ダウンロードさせます。
     */
    @PostMapping("/export-csv")
    public ResponseEntity<Resource> createCSVAndDownload(@RequestParam(required = false) String storeCode,
            @RequestParam(required = false) String yearNumber,
            @RequestParam(required = false) String monthNumber) {

        List<Map<String, Object>> resultList = SalesHistoryService.refineSearchCSV(storeCode, yearNumber, monthNumber);
        String fileName = "sales_history.csv";
        Path filePath = Paths.get(fileName);

        try {
            // 既存のcreateCSVメソッドを呼び出して、サーバーにファイルを作成します。
            SalesHistoryService.createCSV(resultList, fileName);
            
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                // ダウンロード用のヘッダーを設定
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
                headers.add(HttpHeaders.CONTENT_TYPE, "text/csv;charset=Shift_JIS");
                
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                throw new RuntimeException("CSVファイルが見つからないか、読み取れません。");
            }

        } catch (IOException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}