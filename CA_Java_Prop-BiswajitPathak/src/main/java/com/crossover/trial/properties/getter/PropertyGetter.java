package com.crossover.trial.properties.getter;

import java.util.Properties;

import com.crossover.trial.properties.getter.request.PropertyGetRequest;

public abstract class PropertyGetter {
	
	protected final PropertyGetRequest request;
	protected final PropertyGetter nextInChain;
	protected volatile Properties properties;
	
	public PropertyGetter(PropertyGetter next, PropertyGetRequest request) {
		this.request = request;
		this.nextInChain = next;
	}
	
	 public abstract Properties handle(PropertyGetRequest request);
	 
	 protected abstract Properties getProperties();
}
