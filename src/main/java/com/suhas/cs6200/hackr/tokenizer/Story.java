package com.suhas.cs6200.hackr.tokenizer;

import org.apache.solr.common.SolrInputDocument;

public class Story {

	int id;
	String by;
	int score;
	int time;
	String time_ts;
	String title;
	String url;
	String text;
	boolean deleted;
	boolean dead;
	int descendents;
	String author;

	public Story() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getTime_ts() {
		return time_ts;
	}

	public void setTime_ts(String time_ts) {
		this.time_ts = time_ts;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getDescendents() {
		return descendents;
	}

	public void setDescendents(int descendents) {
		this.descendents = descendents;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Story(int id, String by, int score, int time, String time_ts, String title, String url, String text, boolean deleted, boolean dead, int descendents, String author) {
		this.id = id;
		this.by = by;
		this.score = score;
		this.time = time;
		this.time_ts = time_ts;
		this.title = title;
		this.url = url;
		this.text = text;
		this.deleted = deleted;
		this.dead = dead;
		this.descendents = descendents;
		this.author = author;
	}

	@SuppressWarnings("Duplicates")
	public SolrInputDocument toSolrDoc() {

		SolrInputDocument doc = new SolrInputDocument();

		doc.addField("type", "story");

		doc.addField("id", this.id);
		doc.addField("by", this.by);
		doc.addField("hn_score", this.score);
		String []timearr = time_ts.split(" ");
		String formattedTime = timearr[0] + "T" + timearr[1];
		doc.addField("time", formattedTime);
		doc.addField("title", this.title);
		doc.addField("url", this.url);
		doc.addField("text", this.text);
		doc.addField("author", this.author);

		return doc;
	}

	public boolean isDead() {
		return dead;
	}

	public boolean isDeleted() {
		return deleted;
	}
}
