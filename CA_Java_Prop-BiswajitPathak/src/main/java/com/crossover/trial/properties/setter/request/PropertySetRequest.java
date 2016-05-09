package com.crossover.trial.properties.setter.request;

import java.util.Properties;

public class PropertySetRequest {
	private String url;
	private volatile Properties properties;
	
	public PropertySetRequest(String url, Properties properties) {
		this.url = url;
		this.properties = properties;
	}
}
