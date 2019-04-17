package com.suhas.cs6200.hackr.tokenizer;

import org.apache.solr.common.SolrInputDocument;

public class Comment {

	int id;
	String by;
	int score;
	int time;
	String time_ts;
	String text;
	int parent;
	boolean deleted;
	boolean dead;
	int ranking;

	public Comment(int id, String by, int score, int time, String time_ts, String text, int parent, boolean deleted, boolean dead, int ranking) {
		this.id = id;
		this.by = by;
		this.score = score;
		this.time = time;
		this.time_ts = time_ts;
		this.text = text;
		this.parent = parent;
		this.deleted = deleted;
		this.dead = dead;
		this.ranking = ranking;
	}

	public SolrInputDocument toSolrDoc() {

		SolrInputDocument doc = new SolrInputDocument();

		doc.addField("type", "comment");

		doc.addField("id", this.id);
		doc.addField("by", this.by);
		int hn_score = score > ranking ? score : ranking;
		doc.addField("hn_score", hn_score);
		String []timearr = time_ts.split(" ");
		String formattedTime = timearr[0] + "T" + timearr[1];
		doc.addField("time", formattedTime);
		doc.addField("text", this.text);
		doc.addField("ranking", this.ranking);
		doc.addField("parent", this.parent);

		return doc;
	}

	public boolean isDead() {
		return dead;
	}

	public boolean isDeleted() {
		return deleted;
	}
}
