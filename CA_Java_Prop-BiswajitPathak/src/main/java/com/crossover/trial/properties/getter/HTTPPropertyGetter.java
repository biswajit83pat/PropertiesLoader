package com.crossover.trial.properties.getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.crossover.trial.properties.getter.request.PropertyGetRequest;
import com.crossover.trial.properties.handler.FileHandler;
import com.crossover.trial.properties.handler.factory.FileHandlerFactory;
import com.crossover.trial.properties.util.Logger;

public class HTTPPropertyGetter extends PropertyGetter {

	private final static Logger logger = new Logger(); 
	
	public HTTPPropertyGetter(PropertyGetter next, PropertyGetRequest request) {
		super(next, request);
	}

	@Override
	public Properties handle(PropertyGetRequest request) {
		String filePath = request.getUrl();
		synchronized (this) {
			if (filePath.contains("http")) {
				return getProperties();
			}  else if(nextInChain != null){
				return nextInChain.handle(request);
			} else {
				return new Properties();
			}
		}
	}

	@Override
	public Properties getProperties() {
		return readPropertiesFromURL(this.request);
	}
	
	private Properties readPropertiesFromURL(PropertyGetRequest request) {
		Properties prop = new Properties();
		String filePath = request.getUrl();

		// getter
		HttpClient client = new DefaultHttpClient();
		HttpGet httpRequest = new HttpGet(filePath);
		HttpResponse response;
		try {
			response = client.execute(httpRequest);

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer sb = new StringBuffer(1024);

			String line = "";
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			//logger.writeDebugLog(sb.toString());

			String suffix = filePath
					.substring(filePath.lastIndexOf(".") + 1); // extract
																// suffix
			FileHandler fileHandler = FileHandlerFactory
					.getInstance(suffix);
			if (fileHandler != null)
				fileHandler.handle(sb.toString());

		} catch (IOException e) {
			logger.writeErrorStackTrace(e);
		}

		return prop;
	}
	
}
