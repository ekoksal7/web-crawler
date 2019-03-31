package com.example.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Crawler implements Callable {
	  private final String url;
	  private final ExecutorService executor;
	  private final Map<String, SeenUrl> seenUrls;

	  public Crawler(
	      String url,
	      ExecutorService executor,
	      Map<String, SeenUrl> seenUrls) {
		    this.url = url;
		    this.executor = executor;
		    this.seenUrls = seenUrls;
	  }

	  public Object call() {
		  		  
		  
		System.out.println("Call Started thread="+Thread.currentThread());
		
	    List<String> newUrls = extractLinks(); // Very similar to your parse
	    
	    seenUrls.get(url).setInnerUrls(newUrls);
	    
	    for (String newUrl : newUrls) {
	      synchronized(seenUrls) {
	        if (seenUrls.containsKey(newUrl)) {
	          seenUrls.get(newUrl).increaseSeen();
	        } else {
	          seenUrls.put(newUrl, new SeenUrl(newUrl));
	          executor.submit(new Crawler(newUrl, executor, seenUrls));
	        }
	      }
	    }
	    
	    
	    System.out.println("Call Ended thread="+Thread.currentThread());
	    return null;
	  }
	  
	  
	  
	  public List<String> extractLinks(){
			
				
		List<String> urls=new ArrayList<String>();
				
			try {	
				if(isMatch(url)) {
					
					
					Document doc= Jsoup.connect(url).get();
					
			
			    	Elements links = doc.select("a[href]");
			    	
			    	
			    	for (Element link : links) {
			        	String l=link.attr("abs:href");
			        	if(l.endsWith("/"))
			        		l=l.substring(0, l.length()-1);
			        	
			        	
			        	if(isMatch(l)) {
			        		urls.add(l);
			        	}
	
			    	}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    	
			return urls;
		  }
		
		  private boolean isMatch(String s) {
			
			boolean result=false;
			  
			Pattern p1 = Pattern.compile("https://babylonhealth.com");
			Pattern p2 = Pattern.compile("https://babylonhealth.com/.*");
			Pattern p3 = Pattern.compile("https://.*\\.babylonhealth.com");
			Pattern p4 = Pattern.compile("https://.*\\.babylonhealth.com/.*");

			result=(p1.matcher(s).matches() || 
				   p2.matcher(s).matches() || 
				   p3.matcher(s).matches() || 
				   p4.matcher(s).matches());
			
			
			return result;
		  }
	}
