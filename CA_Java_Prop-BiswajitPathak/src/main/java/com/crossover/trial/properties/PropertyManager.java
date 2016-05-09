package com.crossover.trial.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.amazonaws.regions.Region;
import com.crossover.trial.properties.util.Logger;
import com.crossover.trial.properties.util.ParametersMap;
import com.crossover.trial.properties.util.PropertiesTypeResolver;
import com.crossover.trial.properties.util.PropertiesUtil;

public class PropertyManager {

	private static final Logger logger = new Logger();
	private int nThreads = PropertiesUtil.NUM_OF_THREADS;

	private final ExecutorService executor =
	 Executors.newFixedThreadPool(nThreads);
	//private final ExecutorService executor = Executors
			//.newSingleThreadExecutor();

	private final Collection<Callable<Properties>> tasks = new ArrayList<Callable<Properties>>();

	private volatile ParametersMap<Object, Object> finalMap = new ParametersMap<Object, Object>();

	public void init(String[] args) {
		for (int itr = 0; itr < args.length; itr++) {
			if (args[itr] != null && args[itr].trim().length() > 10) {// 10 is
																		// just
																		// a
																		// sanity
																		// check
				PropertyThrottle propertyThrottle = new PropertyThrottle(
						args[itr]);
				tasks.add(propertyThrottle);
			}
		}

		if (tasks.size() > 0) {

			List<Future<Properties>> results = null;
			try {
				results = executor.invokeAll(tasks, 80, TimeUnit.SECONDS);
				tasks.clear();
			} catch (InterruptedException ie) {
				logger.writeErrorStackTrace(ie);
			}

			for (Future<Properties> f : results) {
				try {
					Properties properties = f.get();
					if(properties != null) {
						for(Entry<Object,Object> e: properties.entrySet()) {
							finalMap.put(e.getKey(), PropertiesTypeResolver.getType(e.getValue().toString()));
						}
					}
					logger.writeDebugLog("Outbound : " + properties);
				} catch (InterruptedException e) {
					logger.writeErrorStackTrace(e);
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					logger.writeErrorStackTrace(e);
				}
			}
			results.clear();
			shutDown();
			
			prettyPrint(finalMap);
		}
	}
	
	public String getStringProperty(Object paramObject) {
		return finalMap.getString(paramObject);
	}
	
	public Long getLongProperty(Object paramObject) {
		return finalMap.getLong(paramObject);
	}
	
	public Double getDoubleProperty(Object paramObject) {
		return finalMap.getDouble(paramObject);
	}
	
	public Boolean getBooleanProperty(Object paramObject) {
		return finalMap.getBoolean(paramObject);
	}
	
	public Region getRegionProperty(Object paramObject) {
		return finalMap.getAWSRegions(paramObject);
	}
	
	public Object putInProperties(Object key, Object value) {
		return finalMap.put(key, value);
	}
	
	public void prettyPrint(ParametersMap<Object, Object> map) {
		StringBuffer sb = new StringBuffer(1024*8);
		List<String> values = new ArrayList<String>();
		
		for(Entry<Object,Object> e: map.entrySet()) {
			values.add(e.getKey().toString());
		}
		
		Collections.sort(values, new Comparator<String>() {
			public int compare(String a, String b) {
				return a.compareTo(b);
			}
		});
		
		for(int i = 0; i < values.size(); i++) {
			String key = values.get(i);
			sb.append(key.toLowerCase()).append(", ").append(map.get(key).getClass().getCanonicalName()).append(", ").append(map.get(key)).append("\r\n");
		}

		System.out.println(sb.toString());
	}

	private void shutDown() {
		try {
			logger.writeDebugLog("attempt to shutdown executor");
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.writeErrorLog("tasks interrupted");
		} finally {
			if (!executor.isTerminated()) {
				logger.writeErrorLog("cancel non-finished tasks");
			}
			// (Re-)Cancel if current thread also interrupted
			executor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
			logger.writeDebugLog("shutdown finished");
		}
	}

}
