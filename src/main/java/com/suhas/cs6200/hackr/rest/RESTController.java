package com.suhas.cs6200.hackr.rest;
import com.suhas.cs6200.hackr.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

public class RESTController {

	static Logger log = LoggerFactory.getLogger(RESTController.class);

	private static String requestInfoToString(Request request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.requestMethod());
		sb.append(" " + request.url());
		sb.append(" " + request.body());
		return sb.toString();
	}

	public static void main(String[] args) {
		int port = Constants.PORT;
		port(port);

		staticFiles.location("/public");
		staticFiles.expireTime(600L);
		enableDebugScreen();

		before((request, response) -> {
			log.info(requestInfoToString(request));
		});

		SearchHandler searchHandler = new SearchHandler();

		get("/hello", (req, res) -> "Hello World from " + System.getenv("HOSTNAME") );

		post("/api/search",  (req, res) -> searchHandler.getResults(req,res));

		get("/api/getStoryDetails", (req, res) -> searchHandler.getStoryDetailsById(req,res));

		System.out.println("Server started on port " + port);
	}
}
