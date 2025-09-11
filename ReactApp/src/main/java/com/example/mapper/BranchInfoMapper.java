package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 店舗情報へのデータベースアクセスを担うMyBatis Mapperインターフェース
 */
@Mapper
public interface BranchInfoMapper {

	/**
	 * 指定された条件で店舗情報を検索する
	 * @param prefCode 都道府県コード
	 * @param storeCode 店舗コード
	 * @param offset 検索結果の開始位置
	 * @param limit 取得する件数
	 * @return 検索結果のMapのリスト
	 */
	List<Map<String, Object>> refineSearch(@Param("prefCode") String prefCode, @Param("storeCode") String storeCode,
			@Param("offset") int offset,
			@Param("limit") int limit);
	
	/**
	 * 指定された条件に一致する店舗情報の総件数を取得する
	 * @param prefCode 都道府県コード
	 * @param storeCode 店舗コード
	 * @return 総件数
	 */
	int getTotalCount(@Param("prefCode") String prefCode, @Param("storeCode") String storeCode);
}