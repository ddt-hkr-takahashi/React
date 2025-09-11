package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 所有者情報へのデータベースアクセスを担うMyBatis Mapperインターフェース
 */
@Mapper
public interface OwnerInfoMapper {

	/**
	 * 指定された条件で所有者情報を検索する
	 * @param ownerName 所有者名
	 * @param sex 性別
	 * @param minAge 年齢下限
	 * @param maxAge 年齢上限
	 * @param offset 検索結果の開始位置
	 * @param limit 取得する件数
	 * @return 検索結果のMapのリスト
	 */
	List<Map<String, Object>> refineSearch(
			@Param("ownerName") String ownerName,
			@Param("sex") String sex,
			@Param("minAge") String minAge,
			@Param("maxAge") String maxAge, @Param("offset") int offset,
			@Param("limit") int limit);

	/**
	 * 指定された条件に一致する所有者情報の総件数を取得する
	 * @param ownerName 所有者名
	 * @param sex 性別
	 * @param minAge 年齢下限
	 * @param maxAge 年齢上限
	 * @return 総件数
	 */
	int getTotalCount(@Param("ownerName") String ownerName, @Param("sex") String sex,
			@Param("minAge") String minAge, @Param("maxAge") String maxAge);

}