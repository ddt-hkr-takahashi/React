package com.example.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.GeneralModel;
import com.example.demo.dto.SalesHistoryDto;
import com.example.demo.dto.SearchResult;

/**
 * 要求に対して要件に沿ってSQLを生成するクラス(販売履歴)
 */
@Service
public class SalesHistoryService {

	@Autowired
	private com.example.mapper.SalesHistoryMapper SalesHistoryMapper;
	@Autowired
	private GeneralModel GeneralModel;

	/**
	 * 絞り込み検索（一覧用）を実行
	 * @param storeCode 店舗コード
	 * @param yearNumber 年
	 * @param monthNumber 月
	 * @param page ページ番号
	 * @param size 1ページあたりの表示件数
	 * @return 検索結果のリスト
	 */
	public SearchResult refineSearch(String storeCode, String yearNumber, String monthNumber, int page,
			int size) {

		SearchResult searchResult = new SearchResult();

		// オフセットを計算（0始まり）
		int offset = (page - 1) * size;

		try {
			searchResult.setPopularResultList(SalesHistoryMapper.searchSalesHistory(
					storeCode, yearNumber, monthNumber, size, offset));

			int totalCount = SalesHistoryMapper.countSalesHistory(storeCode, yearNumber, monthNumber);

			searchResult.setResultCountMessage("全 " + totalCount + " 件中 " + ((page - 1) * size + 1) + " 件から "
					+ Math.min(page * size, totalCount) + " 件までを表示しています。");

		} catch (PersistenceException e) {

			searchResult.setResultList(null);
			searchResult.setResultCountMessage("検索中にエラーが発生しました : " + e.getMessage());

		}

		return searchResult;
	}

	/**
	 * グラフ表示用に集計検索を実行
	 * @param storeCode 店舗コード
	 * @param YEAR 年
	 * @param MONTH 月
	 * @return 集計結果のリスト
	 */
	public List<Map<String, Object>> refineSearchGragh(String storeCode, String year, String month) {
		return SalesHistoryMapper.aggregateSalesForGraph(storeCode, year, month);
	}

	/**
	 * CSV出力用に全件検索を実行
	 * @param storeCode 店舗コード
	 * @param YEAR 年
	 * @param MONTH 月
	 * @return 検索結果のリスト
	 */
	public List<Map<String, Object>> refineSearchCSV(String storeCode, String YEAR, String MONTH) {
		return SalesHistoryMapper.searchSalesHistoryForCSV(storeCode, YEAR, MONTH);
	}

	/**
	 * CSVファイルを読み込み、データをデータベースに挿入
	 * @param file アップロードされたCSVファイル
	 * @return
	 * @throws SQLException
	 */
	@Transactional(rollbackFor = Exception.class) // トランザクション管理を追加
	public String insertData(MultipartFile file) {
		long startTime = System.currentTimeMillis();

		try {
			GeneralModel.buildCache();
		} catch (SQLException e1) {
			return "キャッシュの構築に失敗しました。";
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			List<SalesHistoryDto> batchList = new ArrayList<>();
			String CSVDelimiter = ",";
			int batchSize = 1000;

			br.readLine(); // ヘッダー行を読み飛ばす

			while ((line = br.readLine()) != null) {
				String[] data = line.split(CSVDelimiter);

				// データの取得と変換
				String branchName = data[2];
				String parchaseDate = data[3];
				int salesAmount = Integer.parseInt(data[4]);
				String makerName = data[5];
				String typeName = data[6];
				String modelName = data[7];

				// キャッシュからコードを取得
				String modelCode = GeneralModel.carCacheMap
						.get(String.format("%s_%s_%s", makerName, typeName, modelName));
				String branchCode = GeneralModel.branchCacheMap.get(branchName);

				// UsedCarEntityを作成しリストに追加
				SalesHistoryDto car = new SalesHistoryDto();
				car.setBranchCode(branchCode);
				car.setModelCode(modelCode);
				car.setSalesAmount(salesAmount);
				car.setParchaseDate(parchaseDate);
				batchList.add(car);

				// バッチサイズに達したらまとめて挿入
				if (batchList.size() >= batchSize) {
					SalesHistoryMapper.insertUsedCarBatch(batchList);
					batchList.clear();
				}
			}

			// 残りのデータを挿入
			if (!batchList.isEmpty()) {
				SalesHistoryMapper.insertUsedCarBatch(batchList);
			}

			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			System.out.printf("インポート処理にかかった時間: " + (duration / 1000.0) + "秒");
			return null;

		} catch (IOException | RuntimeException e) {
			// エラーメッセージを設定して返す
			return "データのインポート中にエラーが発生しました。詳細: " + e.getMessage();
		}
	}

	/**
	 * CSV出力機能
	 * @param resultList CSVに出力するデータ
	 * @param fileName 
	 */
	public void createCSV(List<Map<String, Object>> resultList, String fileName) throws RuntimeException {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
			writer.println("都道府県,販売店,支店名,販売日,販売額,メーカ,タイプ,車種");
			for (Map<String, Object> row : resultList) {
				writer.printf("%s,%s,%s,%s,%s,%s,%s,%s\n",
						row.get("prefName"),
						row.get("storeName"),
						row.get("branchName"),
						row.get("PD"),
						row.get("salesAmount"),
						row.get("makerName"),
						row.get("typeName"),
						row.get("modelName"));
			}
		} catch (IOException e) {
			throw new RuntimeException("CSVファイルの作成中にエラーが発生しました。", e);
		}
	}
}