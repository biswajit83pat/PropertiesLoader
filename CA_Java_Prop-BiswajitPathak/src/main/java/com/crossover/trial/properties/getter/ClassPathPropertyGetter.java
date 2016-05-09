package com.crossover.trial.properties.getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.crossover.trial.properties.getter.request.PropertyGetRequest;
import com.crossover.trial.properties.handler.FileHandler;
import com.crossover.trial.properties.handler.factory.FileHandlerFactory;
import com.crossover.trial.properties.util.Logger;

public class ClassPathPropertyGetter extends PropertyGetter {

	private static final Logger logger = new Logger();

	public ClassPathPropertyGetter(PropertyGetter next,
			PropertyGetRequest request) {
		super(next, request);
	}

	@Override
	public Properties handle(PropertyGetRequest request) {
		String filePath = request.getUrl();
		synchronized (this) {
			if (filePath.contains("classpath:resources/")) {
				return getProperties();
			} else {
				return nextInChain.handle(request);
			}
		}
	}

	private Properties readPropertiesFromClasspath(PropertyGetRequest request) throws IOException {
		Properties prop = null;
		String filePath = request.getUrl();
		filePath = filePath.substring(20);//As "classpath:resources/" makes 20 characters altogether
		Path path = Paths.get(filePath);
		
		//getter
		InputStream stream = ClassPathPropertyGetter.class.getClassLoader()
	            .getResourceAsStream(path.toString()); 
        if(stream == null) {
        	stream = ClassPathPropertyGetter.class.getResourceAsStream(path.toString());
        }
		
        if(stream != null) {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        	StringBuffer sb = new StringBuffer(1024);

			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line);
					sb.append("\r\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        	String suffix = filePath.substring(filePath.lastIndexOf(".") + 1); //extract suffix
			FileHandler fileHandler = FileHandlerFactory.getInstance(suffix);
			if(fileHandler != null) 
				prop = fileHandler.handle(sb.toString());
			else
				return new Properties();
        }
        
		return prop;
	}
	
	@Override
	public Properties getProperties() {
		try {
			return readPropertiesFromClasspath(this.request);
		} catch (IOException e) {
			logger.writeErrorStackTrace(e);//Catch all the exceptions from abnormal termination of the thread!
		}
		return new Properties();
	}
	

}
