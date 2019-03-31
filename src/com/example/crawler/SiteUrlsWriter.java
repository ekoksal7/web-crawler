package com.example.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SiteUrlsWriter {
	public static final String SITES_URLS_RELATIVE_PATH="/SiteUrls.txt";
	
	private String sitesUrlsFilePath;
	
	
	public SiteUrlsWriter() {
		try {
			String currentPath = new File( "." ).getCanonicalPath();
			sitesUrlsFilePath=currentPath+SITES_URLS_RELATIVE_PATH;
						
			File f=new File(sitesUrlsFilePath);
			
			if(f.exists()) {
				f.delete();
			}
			else {
				f.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void writeToFile(Map<String, SeenUrl> seenUrls) {
		
		System.out.println("Start writing urls to "+this.sitesUrlsFilePath);
		
		BufferedWriter writer =null;
		try {
			writer = new BufferedWriter(
			        new FileWriter(this.sitesUrlsFilePath, true)
			    );
			
			for (String key : seenUrls.keySet()) {
				
				writer.write("url="+key+"\n\n");
		    	
		    	for (String url : seenUrls.get(key).getInnerUrls()) {
		    		writer.write("\t"+url+"\n");
		    		
				}
		    	
		    	writer.write("\n");
			}
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		
		System.out.println("End writing urls to "+this.sitesUrlsFilePath);
		
	}

}
