package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.SalesHistoryDto;

@Mapper
public interface SalesHistoryMapper {

	/**
	 * 販売履歴の総件数を取得します。
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
	 * 販売履歴の絞り込み検索（一覧用）を実行します。
	 * @param storeCode 店舗コード
	 * @param yearNumber 年
	 * @param monthNumber 月
	 * @param size 1ページあたりの表示件数
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
	 * 販売履歴のグラフ表示用に集計検索を実行します。
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
	 * CSV出力用の全件検索を実行します。
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
	 * CSVデータの一括挿入を行います。
	 * @param params 挿入するデータのMap
	 * @return 挿入された行数
	 */
	int insertUsedCar(
			@Param("branchCode") String branchCode,
			@Param("modelCode") String modelCode,
			@Param("salesAmount") int salesAmount,
			@Param("parchaseDate") String parchaseDate);
	
	/**
	 * CSVデータの一括挿入を行います。
	 * @param list 挿入するUsedCarEntityのリスト
	 * @return 挿入された行数
	 */
	int insertUsedCarBatch(@Param("list") List<SalesHistoryDto> list);
	
	
}
