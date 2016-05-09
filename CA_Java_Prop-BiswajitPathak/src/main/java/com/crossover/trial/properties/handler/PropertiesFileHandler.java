package com.crossover.trial.properties.handler;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import com.crossover.trial.properties.util.Logger;

public class PropertiesFileHandler implements FileHandler {

	private static Logger logger = new Logger();

	public Properties handle(String fileContents) {
		final Properties p = new Properties();
		if(fileContents == null || fileContents.trim().isEmpty()) 
			return p;
		try {
			p.load(new StringReader(fileContents));
		} catch (IOException e) {
			logger.writeErrorStackTrace(e);
		}
		return p;
	}

}
