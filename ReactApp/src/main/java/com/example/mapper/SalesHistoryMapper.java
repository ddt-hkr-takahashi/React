package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.SalesHistoryDto;

/**
 * 販売履歴情報へのデータベースアクセスを担うMyBatis Mapperインターフェース
 */
@Mapper
public interface SalesHistoryMapper {

	/**
	 * 指定された条件に一致する販売履歴の総件数を取得する
	 * @param storeCode 店舗コード
	 * @param yearNumber 年
	 * @param monthNumber 月
	 * @return 総件数
	 */
	int countSalesHistory(
			@Param("storeCode") String storeCode,
			@Param("yearNumber") String yearNumber,
			@Param("monthNumber") String monthNumber);

	/**
	 * 販売履歴の絞り込み検索（一覧用）を実行する
	 * @param storeCode 店舗コード
	 * @param yearNumber 年
	 * @param monthNumber 月
	 * @param limit 1ページあたりの表示件数
	 * @param offset 取得開始位置
	 * @return 検索結果のリスト
	 */
	List<Map<String, Object>> searchSalesHistory(
			@Param("storeCode") String storeCode,
			@Param("yearNumber") String yearNumber,
			@Param("monthNumber") String monthNumber,
			@Param("limit") int limit,
			@Param("offset") int offset);

	/**
	 * 販売履歴のグラフ表示用に集計検索を実行する
	 * @param storeCode 店舗コード
	 * @param year 年
	 * @param month 月
	 * @return 集計結果のリスト
	 */
	List<Map<String, Object>> aggregateSalesForGraph(
			@Param("storeCode") String storeCode,
			@Param("year") String year,
			@Param("month") String month);

	/**
	 * CSV出力用の全件検索を実行する
	 * @param storeCode 店舗コード
	 * @param year 年
	 * @param month 月
	 * @return 検索結果のリスト
	 */
	List<Map<String, Object>> searchSalesHistoryForCSV(
			@Param("storeCode") String storeCode,
			@Param("year") String year,
			@Param("month") String month);

	/**
	 * CSVデータの一括挿入を行う
	 * @param branchCode 支店コード
	 * @param modelCode モデルコード
	 * @param salesAmount 販売額
	 * @param parchaseDate 販売日
	 * @return 挿入された行数
	 */
	int insertUsedCar(
			@Param("branchCode") String branchCode,
			@Param("modelCode") String modelCode,
			@Param("salesAmount") int salesAmount,
			@Param("parchaseDate") String parchaseDate);
	
	/**
	 * CSVデータの一括挿入をバッチ処理で行う
	 * @param list 挿入するUsedCarEntityのリスト
	 * @return 挿入された行数
	 */
	int insertUsedCarBatch(@Param("list") List<SalesHistoryDto> list);
	
}