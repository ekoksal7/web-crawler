package com.example.crawler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(30);
	    Map<String, SeenUrl> seenUrls = new LinkedHashMap<>();
	    seenUrls
	      .put("https://babylonhealth.com", new SeenUrl("https://babylonhealth.com"));
	    executorService.submit(
	      new Crawler("https://babylonhealth.com", executorService, seenUrls)); 
	    
	    
	    
	    try {
	    	Thread.currentThread().sleep(180000);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   

	    
	    SiteUrlsWriter siteUrlsWriter=new SiteUrlsWriter();
	    siteUrlsWriter.writeToFile(seenUrls);
	    	    
	    executorService.shutdownNow();

	}

}
