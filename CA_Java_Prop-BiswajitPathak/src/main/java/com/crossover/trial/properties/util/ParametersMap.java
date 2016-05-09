package com.crossover.trial.properties.util;

import java.util.concurrent.ConcurrentHashMap;

import com.amazonaws.regions.Region;

//This class is used to add Type support for 
//each of the parameters whose Object is determined as per the requirements:
//
//1. Assume the solution will need to scale to about 100 properties
//3. Type safety is important and having a type safe accessor to properties is a requirement
//4. The easier and more centralized the addition / removal of properties the better
public class ParametersMap<K,V> extends ConcurrentHashMap<K,V>{

	private static final long serialVersionUID = 1L;
 
	public Region getAWSRegions(Object paramObject) {
		return (Region)this.get(paramObject);
	}
	
	public Long getLong(Object paramObject) {
		return (Long)this.get(paramObject);
	}
	
	public Double getDouble(Object paramObject) {
		return (Double)this.get(paramObject);
	}
	
	public Boolean getBoolean(Object paramObject) {
		return (Boolean)this.get(paramObject);
	}
	
	public String getString(Object paramObject) {
		return (String)this.get(paramObject);
	}
	
}
