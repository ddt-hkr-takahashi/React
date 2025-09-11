package com.example.demo.dto;

import java.util.List;
import java.util.Map;

/**
 * 検索結果を格納するためのデータ転送オブジェクト (DTO)
 */
public class SearchResult {
	/**
	 * 検索結果に関するメッセージ（例：総件数など）
	 */
	private String resultMessage;
	/**
	 * 検索結果のリスト
	 */
	private List<Map<String, Object>> resultList;

	/**
	 * 検索結果に関するメッセージを取得する
	 * @return 検索結果メッセージ
	 */
	public String getResultMessage() {
		return resultMessage;
	}

	/**
	 * 検索結果に関するメッセージを設定する
	 * @param resultMessage 検索結果メッセージ
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * 検索結果のリストを取得する
	 * @return 検索結果リスト
	 */
	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	/**
	 * 検索結果のリストを設定する
	 * @param resultList 検索結果リスト
	 */
	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}
}