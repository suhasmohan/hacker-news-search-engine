package com.suhas.cs6200.hackr.rest;

import java.util.List;
import java.util.Map;

public class SearchResponse {
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SearchResult> getResults() {
		return results;
	}

	public void setResults(List<SearchResult> results) {
		this.results = results;
	}

	List<SearchResult> results;

	Map<String, Map<String, List<String>>> highlighting;

	public Map<String, Map<String, List<String>>> getHighlighting() {
		return highlighting;
	}

	public void setHighlighting(Map<String, Map<String, List<String>>> highlighting) {
		this.highlighting = highlighting;
	}
}
