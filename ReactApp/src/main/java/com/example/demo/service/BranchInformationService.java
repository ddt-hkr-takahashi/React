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
 * 要求に対して要件に沿ってSQLを生成するクラス(店舗情報)
 */
@Service
public class BranchInformationService {

	@Autowired
	private com.example.mapper.BranchInfoMapper BranchInfoMapper;
	@Autowired
	private com.example.mapper.BranchesListMapper BranchesListMapper;

	/**
	 * 絞り込み検索
	 * @param prefCode 県コード
	 * @param storeCode 店舗コード
	 * @param size 
	 * @param page 
	 * @return 検索結果のリスト
	 */
	public SearchResult refineSearch(String prefCode, String storeCode, int page, int size) {

		SearchResult searchResult = new SearchResult();

		// オフセットを計算（0始まり）
		int offset = (page - 1) * size;

		try {
			searchResult.setPopularResultList(BranchInfoMapper.refineSearch(prefCode, storeCode, offset, size));

			int totalCount = BranchInfoMapper.getTotalCount(prefCode, storeCode);

			searchResult.setResultCountMessage("全 " + totalCount + " 件中 " + ((page - 1) * size + 1) + " 件から "
					+ Math.min(page * size, totalCount) + " 件までを表示しています。");

		} catch (PersistenceException e) {

			searchResult.setResultList(null);
			searchResult.setResultCountMessage("検索中にエラーが発生しました : " + e.getMessage());

		}

		return searchResult;
	}

	/**
	 * データの編集
	 * @param prefCode 変更後の県コード
	 * @param storeCode 変更後の店舗コード
	 * @param branchName 変更後の支店名
	 * @param branchCode 編集対象の支店コード
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String editData(String prefCode, String storeCode, String branchName, Integer branchCode) {
		if ((prefCode == null || prefCode.trim().isEmpty()) &&
				(storeCode == null || storeCode.trim().isEmpty()) &&
				(branchName == null || branchName.trim().isEmpty())) {
			return "編集内容が入力されていません。";
		}

		try {
			com.example.model.BranchesList branch = new com.example.model.BranchesList();
			branch.setPrefCode(prefCode);
			branch.setStoreCode(storeCode);
			branch.setBranchName(branchName);
			branch.setBranchCode(branchCode);

			BranchesListMapper.updateByPrimaryKey(branch);
			return null;
		} catch (PersistenceException e) {
			return "編集に失敗しました : " + e.getMessage();
		}
	}

	/**
	 * データを追加
	 * @param prefCode 追加する県コード
	 * @param storeCode 追加する店舗コード
	 * @param branchName 追加する支店名
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String addData(String prefCode, String storeCode, String branchName) {
		if (branchName == null || branchName.trim().isEmpty()) {
			return "支店名が入力されていません。";
		}

		try {
			com.example.model.BranchesList record = new com.example.model.BranchesList();
			record.setPrefCode(prefCode);
			record.setStoreCode(storeCode);
			record.setBranchName(branchName);

			BranchesListMapper.insert(record);
			return null;
		} catch (PersistenceException e) {
			return "データ追加に失敗しました。: " + e.getMessage();
		}
	}

	/**
	 * データを削除
	 * @param branchCodes 削除対象の支店コードの配列
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String deleteData(int[] branchCodes) {
		if (branchCodes == null || branchCodes.length == 0) {
			return "削除対象が選択されていません。";
		}

		List<Integer> branchIdsList = Arrays.stream(branchCodes).boxed().collect(Collectors.toList());

		try {
			BranchesListMapper.delete(c -> c.where(branchCode, isIn(branchIdsList)));
			return null;
		} catch (PersistenceException e) {
			return "削除に失敗しました : " + e.getMessage();
		}
	}
}