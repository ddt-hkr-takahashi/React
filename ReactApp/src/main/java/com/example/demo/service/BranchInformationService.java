package com.example.demo.service;

import static com.example.mapper.BranchesListDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SearchResult;

/**
 * 店舗情報に関するビジネスロジックを提供するサービスクラス
 * <p>
 * 検索、追加、編集、削除などの操作を定義する
 */
@Service
public class BranchInformationService {

	@Autowired
	private com.example.mapper.BranchInfoMapper BranchInfoMapper;
	@Autowired
	private com.example.mapper.BranchesListMapper BranchesListMapper;

	/**
	 * 絞り込み検索を実行する
	 * @param prefCode 県コード
	 * @param storeCode 店舗コード
	 * @param page ページ番号
	 * @param size 1ページあたりの表示件数
	 * @return 検索結果のリスト
	 */
	public SearchResult refineSearch(String prefCode, String storeCode, int page, int size) {
		// 検索結果を格納するオブジェクトを初期化
		SearchResult searchResult = new SearchResult();

		// オフセットを計算（0始まり）
		int offset = (page - 1) * size;

		try {
			// 絞り込み条件に基づいて店舗情報を取得
			searchResult.setResultList(BranchInfoMapper.refineSearch(prefCode, storeCode, offset, size));

			// 検索結果の総件数を取得
			int totalCount = BranchInfoMapper.getTotalCount(prefCode, storeCode);

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
	 * 店舗情報を編集する
	 * @param prefCode 変更後の県コード
	 * @param storeCode 変更後の店舗コード
	 * @param branchName 変更後の支店名
	 * @param branchCode 編集対象の支店コード
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String editData(String prefCode, String storeCode, String branchName, Integer branchCode) {
		// 編集内容の入力チェック
		if ((prefCode == null || prefCode.trim().isEmpty()) &&
				(storeCode == null || storeCode.trim().isEmpty()) &&
				(branchName == null || branchName.trim().isEmpty())) {
			return "編集内容が入力されていません。";
		}

		try {
			// 編集対象のエンティティを生成し、データを設定
			com.example.model.BranchesList branch = new com.example.model.BranchesList();
			branch.setPrefCode(prefCode);
			branch.setStoreCode(storeCode);
			branch.setBranchName(branchName);
			branch.setBranchCode(branchCode);

			// プライマリキーでレコードを更新
			BranchesListMapper.updateByPrimaryKey(branch);
			return null;
		} catch (PersistenceException e) {
			// データベース操作中のエラーを捕捉
			return "編集に失敗しました : " + e.getMessage();
		}
	}

	/**
	 * 店舗情報を追加する
	 * @param prefCode 追加する県コード
	 * @param storeCode 追加する店舗コード
	 * @param branchName 追加する支店名
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String addData(String prefCode, String storeCode, String branchName) {
		// 支店名の入力チェック
		if (branchName == null || branchName.trim().isEmpty()) {
			return "支店名が入力されていません。";
		}

		try {
			// 追加するエンティティを生成し、データを設定
			com.example.model.BranchesList record = new com.example.model.BranchesList();
			record.setPrefCode(prefCode);
			record.setStoreCode(storeCode);
			record.setBranchName(branchName);

			// レコードをデータベースに挿入
			BranchesListMapper.insert(record);
			return null;
		} catch (PersistenceException e) {
			// データベース操作中のエラーを捕捉
			return "データ追加に失敗しました。: " + e.getMessage();
		}
	}

	/**
	 * 店舗情報を削除する
	 * @param branchCodes 削除対象の支店コードの配列
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String deleteData(int[] branchCodes) {
		// 削除対象の入力チェック
		if (branchCodes == null || branchCodes.length == 0) {
			return "削除対象が選択されていません。";
		}
		// プリミティブ配列をListに変換
		List<Integer> branchIdsList = Arrays.stream(branchCodes).boxed().collect(Collectors.toList());

		try {
			// 支店コードのリストに基づいてレコードを削除
			BranchesListMapper.delete(c -> c.where(branchCode, isIn(branchIdsList)));
			return null;
		} catch (PersistenceException e) {
			// データベース操作中のエラーを捕捉
			return "削除に失敗しました : " + e.getMessage();
		}
	}
}