package com.crossover.trial.properties;

import java.util.Properties;
import java.util.concurrent.Callable;

import com.crossover.trial.properties.getter.ClassPathPropertyGetter;
import com.crossover.trial.properties.getter.FilePropertyGetter;
import com.crossover.trial.properties.getter.HTTPPropertyGetter;
import com.crossover.trial.properties.getter.PropertyGetter;
import com.crossover.trial.properties.getter.request.PropertyGetRequest;

public class PropertyThrottle  implements Callable<Properties>{

	private final String url;
	
	public PropertyThrottle(String url) {
		this.url = url;
	}
	
	@Override
	public Properties call() throws Exception {
		PropertyGetRequest request = new PropertyGetRequest(url);
		
		PropertyGetter classPathPropGetter = new ClassPathPropertyGetter(null, request);
		PropertyGetter filePropertyGetter = new FilePropertyGetter(classPathPropGetter, request);
		PropertyGetter hTTPGetter = new HTTPPropertyGetter(filePropertyGetter, request);
	
		return hTTPGetter.handle(request);
	}

}
