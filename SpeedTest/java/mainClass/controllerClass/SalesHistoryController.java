package mainClass.controllerClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import mainClass.serviceClass.SalesHistoryService;

/**
 * 販売情報管理機能用制御クラス
 * @author 高橋輝
 *
 */
@Controller
@RequestMapping("/sales-history-menu")
public class SalesHistoryController extends GeneralControl {

	@Autowired // SalesHistoryServiceのインスタンスを自動的に注入
	private SalesHistoryService SHService;

	/**
	 * 
	 * データ検索機能
	 * 
	 * @param page ページ番号。
	 * @param size 1ページあたりの表示件数。
	 * @param storeCode 検索する店舗のコード。
	 * @param yearNumber 検索する年。
	 * @param monthNumber 検索する月。
	 * @param model
	 * @return 販売履歴一覧画面のテンプレート
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/search-result")
	public String searchFunction(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int size,
			@RequestParam("storeCode") String storeCode,
			@RequestParam("yearNumber") String yearNumber,
			@RequestParam("monthNumber") String monthNumber,
			Model model) {

		List<Map<String, Object>> resultList; // 検索結果を格納するリスト
		String resultCountMessage = null; // 検索結果件数メッセージ

		try {
			// サービスから検索結果と総件数を取得
			Map<String, Object> serviceResult = SHService.refineSearch(storeCode, yearNumber, monthNumber, page, size);

			resultList = (List<Map<String, Object>>) serviceResult.get("resultList"); // 結果リストを取得
			int totalCount = (int) serviceResult.get("totalCount"); // 総件数を取得

			// 総ページ数を計算
			int totalPages = (int) Math.ceil((double) totalCount / size);

			// 検索結果の有無に応じてメッセージを設定
			if (resultList != null && !resultList.isEmpty()) {
				// 検索結果件数メッセージを生成
				resultCountMessage = "全 " + totalCount + " 件中 " + ((page - 1) * size + 1) + " 件から "
						+ Math.min(page * size, totalCount) + " 件までを表示しています。";
			} else {
				resultCountMessage = "検索結果: 0件"; // 検索結果が0件の場合
			}

			// ページネーションと検索結果の情報をモデルに追加
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("resultCountMessage", resultCountMessage);

			// Thymeleafに検索結果リストとメッセージを渡す
			model.addAttribute("resultList", resultList);

			// 検索条件をモデルに追加（ページネーションリンクで再利用）
			model.addAttribute("storeCode", storeCode);
			model.addAttribute("yearNumber", yearNumber);
			model.addAttribute("monthNumber", monthNumber);

		} catch (Exception e) {
			// エラー時の処理
			model.addAttribute("resultList", null);
			model.addAttribute("errorMessage", "検索中にエラーが発生しました。エラー詳細: " + e.getMessage());
			model.addAttribute("resultCountMessage", null);

		}

		return "salesHistoryListMenu"; // 対応するHTMLテンプレートを返す
	}

	/**
	 * グラフ描画に必要なデータをJSON形式で返す。
	 * 
	 * @param storeCode 検索する店舗のコード。
	 * @param YEAR 検索する年。
	 * @param MONTH 検索する月。
	 * @return グラフ描画に必要なデータを含むMap。このMapはJSON形式で返される。
	 */
	@PostMapping("/search-result-gragh")
	@ResponseBody
	public Map<String, Object> showGraph(@RequestParam("storeCode") String storeCode,
			@RequestParam("yearNumber") String yearNumber,
			@RequestParam("monthNumber") String monthNumber) {

		// グラフ用のデータを取得
		List<Map<String, Object>> resultList = SHService.refineSearchGragh(storeCode, yearNumber, monthNumber);

		Map<String, Object> response = new HashMap<>(); // レスポンスデータを格納するマップ
		List<String> labels = new ArrayList<>(); // ラベルリスト
		List<Long> salesAmounts = new ArrayList<>(); // 販売額リスト
		List<Long> salesNumbers = new ArrayList<>(); // 販売台数リスト

		// 取得したデータから必要な情報を抽出
		if (resultList != null && !resultList.isEmpty()) {
			for (Map<String, Object> row : resultList) {
				labels.add((String) row.get("storeName")); // 店舗名をラベルとして追加
				salesAmounts.add(((Number) row.get("salesAmount")).longValue()); // 販売額を追加
				salesNumbers.add(((Number) row.get("numberOfCars")).longValue()); // 販売台数を追加
			}
		}

		response.put("labels", labels); // ラベルを追加
		response.put("salesAmounts", salesAmounts); // 販売額を追加
		response.put("salesNumbers", salesNumbers); // 販売台数を追加
		response.put("yearNumber", yearNumber); // 年を追加
		response.put("monthNumber", monthNumber); // 月を追加

		return response; // グラフデータをJSONとして返す
	}

	/**
	 * CSVファイルの一括取り込み
	 * 
	 * @param file アップロードされたCSVファイル。
	 * @param model
	 * @return 処理結果を表示する販売履歴一覧画面
	 * @throws SQLException 
	 */
	@PostMapping("/import-data")
	public String importCSV(
			@RequestParam("file") MultipartFile file,
			Model model) throws SQLException {

		String returnMessage;
		
		

		returnMessage = SHService.insertData(file); // サービスにファイルを渡してデータ挿入処理を実行
		model.addAttribute("resultCountMessage", returnMessage);

		return "salesHistoryListMenu"; // 元の画面に戻る
	}

	/**
	 * 販売履歴データをCSVファイルとして出力する。
	 * 
	 * @param storeCode 出力するデータの店舗コード。
	 * @param YEAR 出力するデータの年。
	 * @param MONTH 出力するデータの月。
	 * @return CSVファイルのバイト配列とヘッダー
	 * @throws IOException 
	 */
	@PostMapping("/export-data")
	public ResponseEntity<byte[]> exportCSV(@RequestParam("storeCode") String storeCode,
			@RequestParam("YEAR") String YEAR, @RequestParam("MONTH") String MONTH) throws IOException {

		// 検索条件に基づいて全件データを取得
		List<Map<String, Object>> resultList = SHService.refineSearchCSV(storeCode, YEAR, MONTH);

		// サーバーにCSVファイルを生成
		SHService.createCSV(resultList);

		// 生成したファイルを読み込み、バイト配列としてレスポンスを返す
		String fileName = "exportFile.csv";
		try (InputStream is = new FileInputStream(fileName)) { // try-with-resourcesでファイルを自動クローズ
			byte[] csvBytes = FileCopyUtils.copyToByteArray(is);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // バイナリデータとして扱う
			headers.setContentDispositionFormData("attachment", "salesHistory.csv"); // ファイル名を指定してダウンロードを促す

			return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK); // 正常なレスポンスを返す
		}
	}
}