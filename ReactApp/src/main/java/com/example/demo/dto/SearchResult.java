package com.example.demo.dto;

import java.util.List;
import java.util.Map;

public class SearchResult {
	private String resultCountMessage;
	private List<SalesInfoDto> salesResultList;
	private List<Map<String, Object>> popularResultList;

	// getterとsetter
	public String getResultCountMessage() {
		return resultCountMessage;
	}

	public void setResultCountMessage(String resultCountMessage) {
		this.resultCountMessage = resultCountMessage;
	}

	public List<SalesInfoDto> getSalesResultList() {
		return salesResultList;
	}
	
	public List<Map<String, Object>> getPopularResultList() {
		return popularResultList;
	}

	public void setResultList(List<SalesInfoDto> salesResultList) {
		this.salesResultList = salesResultList;
	}

	public void setPopularResultList(List<Map<String, Object>> popularResultList) {
		this.popularResultList = popularResultList;
	}
}