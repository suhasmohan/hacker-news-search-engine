package com.suhas.cs6200.hackr.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SearchResult {
	int id;
	String by;
	int score;


	Date time;

	String title;
	String url;
	String text;
	boolean deleted;
	boolean dead;
	int descendents;
	String author;



	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDead() {
		return dead;
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

	public long getParent() {
		return parent;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	long parent;
	String type;

	public SearchResult() {
	}

	public SearchResult(int id, String by, int score, Date time, String title, String url, String text, boolean deleted, boolean dead, int descendents, String author, long parent, String type) {
		this.id = id;
		this.by = by;
		this.score = score;
		this.time = time;
		this.title = title;
		this.url = url;
		this.text = text;
		this.deleted = deleted;
		this.dead = dead;
		this.descendents = descendents;
		this.author = author;
		this.parent = parent;
		this.type = type;
	}
}
