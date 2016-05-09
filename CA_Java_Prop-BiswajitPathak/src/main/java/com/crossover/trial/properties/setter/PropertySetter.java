package com.crossover.trial.properties.setter;

import com.crossover.trial.properties.setter.request.PropertySetRequest;


public abstract class PropertySetter {

	private PropertySetRequest request;
	private PropertySetter nextInChain;
	
	public PropertySetter(PropertySetter next, PropertySetRequest request) {
		this.request = request;
	}
	
	 public final void start(PropertySetRequest request)
     {
         boolean handledByThisNode = this.handle(request);
         if (nextInChain != null && !handledByThisNode)
            nextInChain.start(request);
     }

	 protected abstract boolean handle(PropertySetRequest request);
}
