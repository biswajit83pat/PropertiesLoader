package com.crossover.trial.properties.getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.crossover.trial.properties.getter.request.PropertyGetRequest;
import com.crossover.trial.properties.handler.FileHandler;
import com.crossover.trial.properties.handler.factory.FileHandlerFactory;
import com.crossover.trial.properties.util.Logger;

public class FilePropertyGetter extends PropertyGetter {

	private final static Logger logger = new Logger();
	
	public FilePropertyGetter(PropertyGetter next, PropertyGetRequest request) {
		super(next, request);
	}

	@Override
	public Properties handle(PropertyGetRequest request) {
		String filePath = request.getUrl();
		synchronized (this) {
			if(filePath.contains("file:")) {
				return getProperties();
			} else if(nextInChain != null){
				return nextInChain.handle(request);
			} else {
				return new Properties();
			}
		}
	}
	
	private Properties readPropertiesFromFileSystem(PropertyGetRequest request) {
		Properties prop = null;
		String filePath = request.getUrl();
		filePath = filePath.substring(5);//As "file:" makes 5 characters altogether
		Path path = Paths.get(filePath);
		
		boolean isRegularFile = Files.isRegularFile(path);
		boolean isHidden = Files.isReadable(path);
		boolean isReadable = Files.isReadable(path);
		boolean isExecutable = Files.isExecutable(path);

		boolean isWritable = Files.isWritable(path);
		
		boolean checkIfFileExists = Files.exists(path);
		
		logger.writeDebugLog(" checkIfFileExists:" + checkIfFileExists + "\n isRegularFile: " + isRegularFile + "\n isHidden:" + isHidden + "\n isReadable:" + isReadable + 
				"\n isExecutable: " + isExecutable + "\n isWritable: " + isWritable);
		
		//getter
		if(checkIfFileExists && isReadable && isRegularFile) {
			String suffix = filePath.substring(filePath.lastIndexOf(".") + 1); //extract suffix
			FileHandler fileHandler = FileHandlerFactory.getInstance(suffix);
			String fileContents = null;
			try {
				fileContents = new String(Files.readAllBytes(path));
			} catch (IOException e) {
				logger.writeErrorStackTrace(e);
			}
			if(fileHandler != null) 
				prop = fileHandler.handle(fileContents);
			else
				return new Properties();
		}
		
		return prop;
	}
	
	@Override
	public Properties getProperties() {
		return readPropertiesFromFileSystem(this.request);
	}
	
}
