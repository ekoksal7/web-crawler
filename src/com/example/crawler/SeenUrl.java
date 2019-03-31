package com.example.crawler;

import java.util.List;

public class SeenUrl {
	
	private String url;
	private int timesSeen=1;
	private List<String> innerUrls;
	
	
	public SeenUrl(String url) {
		this.url=url;
	}
	
	public void increaseSeen() {
		timesSeen++;
	}

	public List<String> getInnerUrls() {
		return innerUrls;
	}

	public void setInnerUrls(List<String> innerUrls) {
		this.innerUrls = innerUrls;
	}
	
}
