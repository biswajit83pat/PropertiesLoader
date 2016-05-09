package com.crossover.trial.properties.setter;

import com.crossover.trial.properties.setter.request.PropertySetRequest;

public class HTTPPropertySetter extends PropertySetter {

	private PropertySetter nextInChain;
	
	public HTTPPropertySetter(PropertySetter next, PropertySetRequest request) {
		super(next, request);
		this.nextInChain = next;
	}

	@Override
	protected boolean handle(PropertySetRequest request) {
		//TODO add code here
		return false;
	}
	
}
