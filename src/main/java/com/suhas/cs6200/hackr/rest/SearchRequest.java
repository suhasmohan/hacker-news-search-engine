package com.suhas.cs6200.hackr.rest;

public class SearchRequest {
	public String getQ() {
		return q;
	}

	String q;

	public String getSort() {
		return sort;
	}

	String sort;

	int page;

	public int getPage() {
		return page;
	}

	public SearchRequest(String q) {
		this.q = q;
	}
}
