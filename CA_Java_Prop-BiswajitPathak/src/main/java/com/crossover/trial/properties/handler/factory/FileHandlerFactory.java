package com.crossover.trial.properties.handler.factory;

import java.util.Hashtable;

import com.crossover.trial.properties.handler.FileHandler;
import com.crossover.trial.properties.handler.JSONFileHandler;
import com.crossover.trial.properties.handler.PropertiesFileHandler;

public class FileHandlerFactory {

	private static volatile Hashtable<String, FileHandler> fileHandlerMap = new Hashtable<String, FileHandler>();

	static {
		fileHandlerMap.put("properties", new PropertiesFileHandler());
		fileHandlerMap.put("json", new JSONFileHandler());
	}

	public static FileHandler getInstance(String suffix) {
		return fileHandlerMap.get(suffix);
	}

}
