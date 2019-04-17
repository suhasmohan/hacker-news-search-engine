package com.suhas.cs6200.hackr.rest;

public class SearchRequest {
	public String getQ() {
		return q;
	}

	String q;

	public SearchRequest(String q) {
		this.q = q;
	}
}
