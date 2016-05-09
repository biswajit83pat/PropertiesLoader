package com.crossover.trial.properties;

import com.crossover.trial.properties.util.Logger;

public class Main {
	
	private static final Logger logger = new Logger();
	
	public static void main(String[] args) {
		logger.writeDebugLog("args contents --> " + args);
		PropertyManager propertyManager = new PropertyManager();
		propertyManager.init(args);
	}
	
}