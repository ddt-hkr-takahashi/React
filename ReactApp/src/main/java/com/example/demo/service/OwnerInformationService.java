package com.example.demo.service;

import static com.example.mapper.OwnersListDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SearchResult;
import com.example.mapper.OwnerInfoMapper;

/**
 * 要求に対して要件に沿ってSQLを生成するクラス(元所有者情報)
 * @author 高橋輝
 */
@Service
public class OwnerInformationService {

	@Autowired
	private OwnerInfoMapper OwnerInfoMapper;
	@Autowired
	private com.example.mapper.OwnersListMapper OwnersListMapper;

	/**
	 * 絞り込み検索
	 * @param ownerName 元所有者名
	 * @param gender 性別
	 * @param minAge 年齢（下限）
	 * @param maxAge 年齢（上限）
	 * @return 検索結果のリスト
	 */
	public SearchResult refineSearch(String ownerName, String gender, String minAge, String maxAge, int page,
			int size) {
		SearchResult searchResult = new SearchResult();

		// オフセットを計算（0始まり）
		int offset = (page - 1) * size;
		int limit = size;

		try {
			searchResult.setPopularResultList(OwnerInfoMapper.refineSearch(
					ownerName, gender, minAge, maxAge, offset, limit));

			int totalCount = OwnerInfoMapper.getTotalCount(ownerName, gender, minAge, maxAge);

			searchResult.setResultCountMessage("全 " + totalCount + " 件中 " + ((page - 1) * size + 1) + " 件から "
					+ Math.min(page * size, totalCount) + " 件までを表示しています。");

		} catch (PersistenceException e) {

			searchResult.setResultList(null);
			searchResult.setResultCountMessage("検索中にエラーが発生しました : " + e.getMessage());

		}

		return searchResult;
	}

	/**
	 * データを追加
	 * @param ownerName 元所有者名
	 * @param gender 性別
	 * @param age 年齢
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String addData(String ownerName, String gender, String age) {
		// 未入力を検知
		if (ownerName == null || ownerName.trim().isEmpty()) {
			return "所有者名が入力されていません。";
		}
		if (age == null || age.trim().isEmpty()) {
			return "年齢が入力されていません。";
		}

		int parsedAge;
		try {
			parsedAge = Integer.parseInt(age);
			if (parsedAge < 0) {
				return "年齢は0以上の値を入力してください。";
			}
		} catch (NumberFormatException e) {
			return "年齢は数値で入力してください。";
		}

		try {
			// INSERT文を構築
			com.example.model.OwnersList record = new com.example.model.OwnersList();
			record.setOwnerName(ownerName);
			record.setSex(gender);
			record.setAge(parsedAge);
			OwnersListMapper.insert(record);
			return null;
		} catch (PersistenceException e) {
			return "データ追加に失敗しました。: " + e.getMessage();
		}
	}

	/**
	 * データを編集
	 * @param ownerName 元所有者名
	 * @param gender 性別
	 * @param age 年齢
	 * @param ownerCode 識別番号
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String editData(String ownerName, String gender, String age, String ownerCode) {
		// どの項目も空文字でないかチェックする
		if ((ownerName == null || ownerName.trim().isEmpty())
				&& (gender == null || gender.trim().isEmpty())
				&& (age == null || age.trim().isEmpty())) {
			return "編集内容が入力されていません。";
		}

		Integer parsedAge = null;
		// ageが入力されている場合、数値に変換し、検証を行う
		if (age != null && !age.trim().isEmpty()) {
			try {
				parsedAge = Integer.parseInt(age);
				//0以下の場合
				if (parsedAge < 0) {
					return "年齢は0以上の値を入力してください。";
				}
			}
			//数字以外の場合
			catch (NumberFormatException e) {
				return "年齢は数値で入力してください。";
			}
		}

		
		com.example.model.OwnersList Owner = new com.example.model.OwnersList();
		Owner.setOwnerName(ownerName);
		Owner.setSex(gender);
		Owner.setAge(parsedAge);
		Owner.setOwnerCode(Integer.parseInt(ownerCode));

		try {
			OwnersListMapper.updateByPrimaryKey(Owner); // データベース更新を実行
			return null;
		} catch (PersistenceException e) {
			return "編集に失敗しました : " + e.getMessage();
		}
	}

	/**
	 * データを削除
	 * @param ownerCodes 削除対象の識別番号の配列
	 */
	public String deleteData(int[] ownerCodes) {
		if (ownerCodes == null || ownerCodes.length == 0) {
			return "削除対象が選択されていません。";
		}

		// int配列をList<Integer>に変換
		List<Integer> ownerCodesList = Arrays.stream(ownerCodes).boxed().collect(Collectors.toList());

		try {
			// MyBatis Dynamic SQLのisInを使って一括削除
			OwnersListMapper.delete(c -> c.where(ownerCode, isIn(ownerCodesList)));
			return null;
		} catch (PersistenceException e) {
			return "削除に失敗しました : " + e.getMessage();
		}
	}
}