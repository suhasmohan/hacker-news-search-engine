package com.suhas.cs6200.hackr.solr;

import com.suhas.cs6200.hackr.rest.SearchResponse;
import com.suhas.cs6200.hackr.rest.SearchResult;
import com.suhas.cs6200.hackr.tokenizer.Story;
import com.suhas.cs6200.hackr.utils.Constants;
import com.suhas.cs6200.hackr.utils.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolrQueryHandler {

	HttpSolrClient solr;

	public SolrQueryHandler(String collectionName) {
		String urlString = "http://localhost:8983/solr/" + collectionName;
		solr = new HttpSolrClient.Builder(urlString).build();
		solr.setParser(new XMLResponseParser());
	}

	public SearchResponse getResults(String query, String sort, int pageNo) {
		List<SearchResult> results = new ArrayList<>();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("defType", "edismax");
		solrQuery.set("q", "\"" + query + "\"");
		solrQuery.set("stopwords", true);
		if (!sort.equalsIgnoreCase(Constants.RELEVANCE_SORT))
			solrQuery.set("sort", sort + " desc");
		solrQuery.set("start", pageNo * Constants.RESULTS_PER_PAGE);
		solrQuery.set("rows", Constants.RESULTS_PER_PAGE);
		solrQuery.set("qf", "title^1.5 + text^1.2");
		solrQuery.set("bf", "hn_score");
		solrQuery.setHighlight(true).setHighlightSnippets(1).setHighlightFragsize(200);
		solrQuery.setParam("hl.fl", "*");
		solrQuery.setParam("hl.usePhraseHighlighter", "true");

		QueryResponse response = null;
		try {
			response = solr.query(solrQuery);
		} catch (SolrServerException e) {
			Logger.log("Solr Server exception : " + e.getLocalizedMessage());
		} catch (IOException e) {
			Logger.log("IO exception: " + e.getMessage());
		}

		SolrDocumentList docList = response.getResults();

		System.out.println("Num results = " + docList.getNumFound());
		for (SolrDocument doc : docList) {
			SearchResult result = new SearchResult();
			result.setId(Integer.parseInt((String) doc.getFieldValue("id")));
			result.setType((String) doc.getFieldValue("type"));
			result.setAuthor((String) doc.getFieldValue("author"));
			result.setBy((String) doc.getFieldValue("by"));
			result.setScore((int) doc.getFieldValue("hn_score"));
			result.setText((String) doc.getFieldValue("text"));
			result.setTitle((String) doc.getFieldValue("title"));
			result.setUrl((String) doc.getFieldValue("url"));
			if (doc.getFieldValue("parent") != null)
				result.setParent((long) doc.getFieldValue("parent"));
			result.setTime((Date) doc.getFieldValue("time"));

			results.add(result);
		}

		SearchResponse resp = new SearchResponse();

		resp.setResults(results);
		resp.setHighlighting(response.getHighlighting());
		resp.setNumResults(docList.getNumFound());
		return resp;
	}

	public Story getStoryDetailsById(String id) {
		List<SearchResult> results = new ArrayList<>();
		SolrQuery solrQuery = new SolrQuery();

		solrQuery.set("q", "id:" + id);

		QueryResponse response = null;
		try {
			response = solr.query(solrQuery);
		} catch (SolrServerException e) {
			Logger.log("Solr Server exception : " + e.getLocalizedMessage());
		} catch (IOException e) {
			Logger.log("IO exception: " + e.getMessage());
		}

		SolrDocumentList docList = response.getResults();
		Story result = new Story();
		for (SolrDocument doc : docList) {
			result.setId(Integer.parseInt((String) doc.getFieldValue("id")));
			result.setAuthor((String) doc.getFieldValue("author"));
			result.setBy((String) doc.getFieldValue("by"));
			result.setScore((int) doc.getFieldValue("hn_score"));
			result.setText((String) doc.getFieldValue("text"));
			result.setTitle((String) doc.getFieldValue("title"));
			result.setUrl((String) doc.getFieldValue("url"));
		}

		return result;

	}
}
