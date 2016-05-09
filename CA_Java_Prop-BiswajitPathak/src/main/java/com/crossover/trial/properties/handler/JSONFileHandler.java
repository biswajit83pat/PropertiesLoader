package com.crossover.trial.properties.handler;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.crossover.trial.properties.util.Logger;

public class JSONFileHandler implements FileHandler{

	private static Logger logger = new Logger();
	
	public Properties handle(String fileContents) {
		JSONParser parser = new JSONParser();
		Properties prop = new Properties();
		
		if(fileContents == null || fileContents.trim().isEmpty()) 
			return prop;
		
		try {
			Object obj = parser.parse(fileContents);

			JSONObject jsonObject = (JSONObject) obj;

			
			if(jsonObject.entrySet() != null) {
				Set<Entry<String,Object>> entries = jsonObject.entrySet();
				for(Entry entry: entries) {
					prop.put(entry.getKey(), entry.getValue());
				}
			}
			
		} catch (org.json.simple.parser.ParseException e) {
			logger.writeErrorStackTrace(e);
		}
		return prop;
	}
	
}
