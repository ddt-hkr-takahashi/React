package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

import com.example.model.BranchesList;

/**
 * 販売情報へのデータベースアクセスを担うMyBatis Mapperインターフェース
 */
@Mapper
public interface SalesInfoMapper {

	/**
	 * 指定された条件で中古車情報を検索する
	 * @param prefCode 都道府県コード
	 * @param branchCode 支店コード
	 * @param makerCode メーカーコード
	 * @param typeCode 車種コード
	 * @param modelName モデル名
	 * @param offset 検索結果の開始位置
	 * @param limit 取得する件数
	 * @return 検索結果のMapのリスト
	 * @throws PersistenceException データアクセスエラーが発生した場合
	 */
	List<Map<String, Object>> searchUsedCars (
			@Param("prefCode") String prefCode,
			@Param("branchCode") String branchCode,
			@Param("makerCode") String makerCode,
			@Param("typeCode") String typeCode,
			@Param("modelName") String modelName,
			@Param("offset") int offset,
			@Param("limit") int limit) throws PersistenceException;

	/**
	 * 指定された条件に一致する中古車情報の総件数を取得する
	 * @param prefCode 都道府県コード
	 * @param branchCode 支店コード
	 * @param makerCode メーカーコード
	 * @param typeCode 車種コード
	 * @param modelName モデル名
	 * @return 総件数
	 * @throws PersistenceException データアクセスエラーが発生した場合
	 */
	int countUsedCars(
			@Param("prefCode") String prefCode,
			@Param("branchCode") String branchCode,
			@Param("makerCode") String makerCode,
			@Param("typeCode") String typeCode,
			@Param("modelName") String modelName) throws PersistenceException;
	
	/**
	 * メーカー名と車種名を結合したモデル情報をすべて取得する
	 * @return 結合されたモデル名を含むMapのリスト
	 * @throws PersistenceException データアクセスエラーが発生した場合
	 */
	List<Map<String, Object>> selectModelsWithCombinedName() throws PersistenceException;
	
	/**
	 * 都道府県コードに基づいて支店情報を検索する
	 * @param prefCode 都道府県コード
	 * @return 検索結果のBranchesListのリスト
	 * @throws PersistenceException データアクセスエラーが発生した場合
	 */
	List<BranchesList> findByPrefCode(@Param("prefCode") String prefCode)throws PersistenceException;
}