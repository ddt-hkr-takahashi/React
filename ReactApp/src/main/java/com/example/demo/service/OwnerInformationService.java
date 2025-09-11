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
 * 所有者情報に関するビジネスロジックを提供するサービスクラス
 */
@Service
public class OwnerInformationService {

	@Autowired
	private OwnerInfoMapper OwnerInfoMapper;
	@Autowired
	private com.example.mapper.OwnersListMapper OwnersListMapper;

	/**
	 * 絞り込み検索を実行する
	 * @param ownerName 所有者名 (任意)
	 * @param gender 性別 (任意)
	 * @param minAge 年齢（下限） (任意)
	 * @param maxAge 年齢（上限） (任意)
	 * @param page ページ番号
	 * @param size 1ページあたりの表示件数
	 * @return 検索結果のリスト
	 */
	public SearchResult refineSearch(String ownerName, String gender, String minAge, String maxAge, int page,
			int size) {
		// 検索結果を格納するオブジェクトを初期化
		SearchResult searchResult = new SearchResult();

		// オフセットとリミットを計算
		int offset = (page - 1) * size;
		int limit = size;

		try {
			// 絞り込み条件に基づいて所有者情報を取得
			searchResult.setResultList(OwnerInfoMapper.refineSearch(
					ownerName, gender, minAge, maxAge, offset, limit));

			// 検索結果の総件数を取得
			int totalCount = OwnerInfoMapper.getTotalCount(ownerName, gender, minAge, maxAge);

			// 結果件数メッセージを設定
			searchResult.setResultMessage("全 " + totalCount + " 件中 " + ((page - 1) * size + 1) + " 件から "
					+ Math.min(page * size, totalCount) + " 件までを表示しています。");

		} catch (PersistenceException e) {
			// データベース操作中のエラーを捕捉し、エラーメッセージを設定
			searchResult.setResultList(null);
			searchResult.setResultMessage("検索中にエラーが発生しました : " + e.getMessage());

		}

		return searchResult;
	}

	/**
	 * 所有者情報を追加する
	 * @param ownerName 所有者名
	 * @param gender 性別
	 * @param age 年齢
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String addData(String ownerName, String gender, String age) {
		// 入力値の検証
		if (ownerName == null || ownerName.trim().isEmpty()) {
			return "所有者名が入力されていません。";
		}
		if (age == null || age.trim().isEmpty()) {
			return "年齢が入力されていません。";
		}

		int parsedAge;
		try {
			// 年齢を数値に変換し、正の数か確認
			parsedAge = Integer.parseInt(age);
			if (parsedAge < 0) {
				return "年齢は0以上の値を入力してください。";
			}
		} catch (NumberFormatException e) {
			// 数値変換エラーを捕捉
			return "年齢は数値で入力してください。";
		}

		try {
			// INSERTするエンティティを構築
			com.example.model.OwnersList record = new com.example.model.OwnersList();
			record.setOwnerName(ownerName);
			record.setSex(gender);
			record.setAge(parsedAge);
			// データベースにレコードを挿入
			OwnersListMapper.insert(record);
			return null;
		} catch (PersistenceException e) {
			// データベース操作中のエラーを捕捉
			return "データ追加に失敗しました。: " + e.getMessage();
		}
	}

	/**
	 * 所有者情報を編集する
	 * @param ownerName 変更後の所有者名
	 * @param gender 変更後の性別
	 * @param age 変更後の年齢
	 * @param ownerCode 編集対象の所有者コード
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String editData(String ownerName, String gender, String age, String ownerCode) {
		// 編集内容の入力チェック
		if ((ownerName == null || ownerName.trim().isEmpty())
				&& (gender == null || gender.trim().isEmpty())
				&& (age == null || age.trim().isEmpty())) {
			return "編集内容が入力されていません。";
		}

		Integer parsedAge = null;
		// 年齢が入力されている場合のみ、数値変換と検証を行う
		if (age != null && !age.trim().isEmpty()) {
			try {
				parsedAge = Integer.parseInt(age);
				//0以下の場合
				if (parsedAge < 0) {
					return "年齢は0以上の値を入力してください。";
				}
			}
			// 数字以外の場合
			catch (NumberFormatException e) {
				return "年齢は数値で入力してください。";
			}
		}

		// 編集対象のエンティティを生成し、データを設定
		com.example.model.OwnersList Owner = new com.example.model.OwnersList();
		Owner.setOwnerName(ownerName);
		Owner.setSex(gender);
		Owner.setAge(parsedAge);
		Owner.setOwnerCode(Integer.parseInt(ownerCode));

		try {
			// データベース更新を実行
			OwnersListMapper.updateByPrimaryKey(Owner);
			return null;
		} catch (PersistenceException e) {
			// データベース操作中のエラーを捕捉
			return "編集に失敗しました : " + e.getMessage();
		}
	}

	/**
	 * 所有者情報を削除する
	 * @param ownerCodes 削除対象の所有者コードの配列
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String deleteData(int[] ownerCodes) {
		// 削除対象の入力チェック
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
			// データベース操作中のエラーを捕捉
			return "削除に失敗しました : " + e.getMessage();
		}
	}
}