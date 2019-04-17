package com.suhas.cs6200.hackr.rest;

import com.google.gson.Gson;
import com.suhas.cs6200.hackr.solr.SolrQueryHandler;
import com.suhas.cs6200.hackr.tokenizer.Story;
import com.suhas.cs6200.hackr.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class SearchHandler {
	static Logger log = LoggerFactory.getLogger(RESTController.class);
	SolrQueryHandler queryHandler = new SolrQueryHandler(Constants.COLLECTION_NAME);

	public Object getResults(Request req, Response res) {
		SearchRequest searchRequest = new Gson().fromJson(req.body(), SearchRequest.class);
		String searchQuery = searchRequest.getQ();
		String sort = searchRequest.getSort();
		int page = searchRequest.getPage();
		log.info("Got search term " + searchQuery);
		log.info("Got sort " + sort);
		log.info("Got page " + page);
		SearchResponse resp = queryHandler.getResults(searchQuery, sort, page);
		resp.setStatus("success");
		return new Gson().toJson(resp);
	}

	public Object getStoryDetailsById(Request req, Response res) {
		String id = req.queryParams("id");
		log.info("Getting story by id: " + id);
		Story story = queryHandler.getStoryDetailsById(id);

		return new Gson().toJson(story);
	}
}
