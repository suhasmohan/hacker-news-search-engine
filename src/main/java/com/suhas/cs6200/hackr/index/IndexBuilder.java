package com.suhas.cs6200.hackr.index;

import com.suhas.cs6200.hackr.tokenizer.Tokenizer;
import com.suhas.cs6200.hackr.utils.Constants;
import com.suhas.cs6200.hackr.utils.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.common.SolrInputDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class to build the Inverted Index
 *
 * @author suhas
 */
public class IndexBuilder {

	/**
	 * Generates an inverted index for all files in the specified path
	 *
	 * @param path
	 * @return Inverted index of all files in path
	 */
	public void buildIndex(String path, String collectionName) {
		// Iterate through each file, tokenizeStories and build index

		Tokenizer tokenizer = new Tokenizer();

		try {
			List<SolrInputDocument> tupleList = new ArrayList<>();
			Logger.log("Tokenizing Stories...");

////			// Iterate over all files in the specified directory
			File dir = new File(path + Constants.STORY_DIR);
			File[] directoryListing = dir.listFiles();
//			if (directoryListing != null) {
//				for (File child : directoryListing) {
//					tupleList.addAll(tokenizer.tokenizeStories(child));
//				}
//			}

			Logger.log("Tokenizing comments...");
			dir = new File(path + Constants.COMMENTS_DIR);
			directoryListing = dir.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {
					if (child.getName().contains("json")) {
						//tupleList.addAll(tokenizer.tokenizeComments(child));
						addDocsToSolr(tokenizer.tokenizeComments(child), collectionName);
					}
				}
			}

			Logger.log("Done tokenizing document!");
			//createSolrSchema(collectionName);
//			addDocsToSolr(tupleList, collectionName);

		} catch (IOException e) {
			Logger.log("I/O Exception! " + e.getMessage());
			return;
		} catch (SolrServerException e) {
			Logger.log("Solr Exception! " + e.getMessage());
			return;
		}

		return;
	}

	private void createSolrSchema(String collectionName) {

		String urlString = "http://localhost:8983/solr/" + collectionName;
		HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
		solr.setParser(new XMLResponseParser());

		Logger.log("Creating schema in Solr");
		try {
			addFieldToSchema(solr, "title", "text_general");
			addFieldToSchema(solr, "text", "text_general");
			addFieldToSchema(solr, "type", "text_general");
			addFieldToSchema(solr, "author", "text_general");
			addFieldToSchema(solr, "by", "text_general");
			addFieldToSchema(solr, "url", "text_general");

		} catch (SolrServerException e) {
		} catch (IOException e) {
		}
		Logger.log("Done creating schema!");

	}

	private void addFieldToSchema(HttpSolrClient solrClient, String fieldName, String fieldType) throws IOException, SolrServerException {
		Map<String, Object> fieldAttributes = new LinkedHashMap<>();
		fieldAttributes.put("name", fieldName);
		fieldAttributes.put("type", fieldType);
		fieldAttributes.put("stored", true);
		fieldAttributes.put("multiValued", false);
		SchemaRequest.AddField addFieldRequest = new SchemaRequest.AddField(fieldAttributes);
		addFieldRequest.process(solrClient);
	}

	private void addDocsToSolr(List<SolrInputDocument> docList, String collectionName) throws IOException, SolrServerException {

		String urlString = "http://localhost:8983/solr/" + collectionName;
		HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
		solr.setParser(new XMLResponseParser());
		Logger.log("Adding docs to Solr");
		solr.add(docList);
		solr.commit();
		Logger.log("Done adding docs to Solr!");

	}
}
