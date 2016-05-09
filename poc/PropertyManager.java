package com.crossover.trial.properties.poc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.crossover.trial.properties.getter.ClassPathPropertyGetter;
import com.crossover.trial.properties.getter.FilePropertyGetter;
import com.crossover.trial.properties.getter.HTTPPropertyGetter;
import com.crossover.trial.properties.getter.PropertyGetter;
import com.crossover.trial.properties.getter.request.PropertyGetRequest;
import com.crossover.trial.properties.setter.PropertySetter;
import com.crossover.trial.properties.util.PropertiesUtil;

public class PropertyManager {
	PropertySetter propertySetter;
	PropertyGetter propertyGetter;
	
	int nThreads = PropertiesUtil.NUM_OF_THREADS;  
	
	ExecutorService executor = Executors.newFixedThreadPool(nThreads);

	private volatile Map<String, Object> finalMap = new ConcurrentHashMap<String, Object>();
	
	public void init() {
		String url = "";
		PropertyGetRequest request = new PropertyGetRequest(url);
		
		PropertyGetter classPathPropGetter = new ClassPathPropertyGetter(null, request);
		PropertyGetter filePropertyGetter = new FilePropertyGetter(classPathPropGetter, request);
		PropertyGetter hTTPGetter = new HTTPPropertyGetter(filePropertyGetter, request);
	
		hTTPGetter.handle(request);
	}
	
	private void executeThreadsInParallel() {
		
	}
	
	private void shutDown() {
		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}
	}
	
}
