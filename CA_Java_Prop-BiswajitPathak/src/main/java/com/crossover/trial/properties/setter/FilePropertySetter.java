package com.crossover.trial.properties.setter;

import java.nio.file.Files;
import java.util.Properties;

import com.crossover.trial.properties.setter.request.PropertySetRequest;

public class FilePropertySetter extends PropertySetter {

	private PropertySetter nextInChain;
	
	public FilePropertySetter(PropertySetter next, PropertySetRequest request) {
		super(next, request);
		this.nextInChain = next;
	}

	@Override
	protected boolean handle(PropertySetRequest request) {
		//TODO add code here
		return false;
	}
	
	private Properties getFileContent(PropertySetRequest request) {
		
		return null;
	}
	
}
