package com.crossover.trial.properties.poc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.crossover.trial.properties.handler.FileHandler;
import com.crossover.trial.properties.handler.factory.FileHandlerFactory;

public class TryJSONHandle {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath = "file://inscsws20001919/Rajesh Share Fldr/amazon1.json";
		String classPathFile = "classpath:resources/test1.properties";
		String httpFilePath = "https://drive.google.com/file/d/0B6k_oHEMrW8PbFJmTTA3RkxqT3M/view"; 
		
		String suffix = filePath.substring(filePath.lastIndexOf(".") + 1); //extract suffix
		
		String fileStringPath = "C:\\Users\\50120i1055\\Desktop\\Properties\\config.json";
		
		//parseJSON(new String(Files.readAllBytes(Paths.get(fileStringPath))));
		
		//checkFile(filePath);
		checkClassPath(classPathFile);
		//checkHttp(httpFilePath);
		/*try {
			URI uri = new URI("file:\\\\C:\\Users\\50120i1055\\daemonprocess.txt");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//System.out.println(Files.exists(Paths.get("file:\\\\C:\\Users\\50120i1055\\daemonprocess.txt"), null));
		//System.out.println(Files.exists(Paths.get("file:\\\\C:\\Users\\50120i1055\\daemonprocess.txt"), null));
		
	}
	
	private static void checkFile(String filePath){
		if(filePath.contains("file:")) {
			filePath = filePath.substring(5);//As "file:" makes 5 characters altogether
			Path path = Paths.get(filePath);
			
			boolean isRegularFile = Files.isRegularFile(path);
			boolean isHidden = Files.isReadable(path);
			boolean isReadable = Files.isReadable(path);
			boolean isExecutable = Files.isExecutable(path);
			boolean isSymbolicLink = Files.isSymbolicLink(path);

			//boolean isDirectory = Files.isDirectory(path);
			boolean isWritable = Files.isWritable(path);
			
			boolean checkIfFileExists = Files.exists(path);
			
			System.out.println(" checkIfFileExists:" + checkIfFileExists + "\n isRegularFile: " + isRegularFile + "\n isHidden:" + isHidden + "\n isReadable:" + isReadable + 
					"\n isExecutable: " + isExecutable + "\n isSymbolicLink: " + isSymbolicLink + "\n isWritable: " + isWritable);
			

			//getter
			if(checkIfFileExists && isReadable && isRegularFile) {
				String fileContents = null; 
				
				try {
					fileContents = new String(Files.readAllBytes(path));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				String suffix = filePath.substring(filePath.lastIndexOf(".") + 1); //extract suffix
				FileHandler fileHandler = FileHandlerFactory.getInstance(suffix);
				if(fileHandler != null)fileHandler.handle(fileContents);
			}
		}
	}
	
	private static void checkClassPath(String filePath){
		if(filePath.contains("classpath:resources/")) {
			filePath = filePath.substring(20);//As "classpath:resources/" makes 20 characters altogether
			Path path = Paths.get(filePath);
			
			//getter
			InputStream stream = TryJSONHandle.class.getClassLoader()
		            .getResourceAsStream(path.toString()); 
			System.out.println("getClassLoader approach: " + (stream != null));
	        if(stream == null) {
	        	stream = TryJSONHandle.class.getResourceAsStream(File.pathSeparator + path.toString());
	        	System.out.println(stream != null);
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
				Properties prop = new Properties();
				if(fileHandler != null)
					prop = fileHandler.handle(sb.toString());
				System.out.println("Classpath --> " + prop);
	        }
		}
	}
	
	private static void checkHttp(String filePath) {
		if (filePath.contains("http")) {
			
			// getter
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(filePath);
			HttpResponse response;
			try {
				response = client.execute(request);

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				StringBuffer sb = new StringBuffer(1024);

				String line = "";
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}

				System.out.println(sb.toString());
				
				String suffix = filePath.substring(filePath.lastIndexOf(".") + 1); //extract suffix
				FileHandler fileHandler = FileHandlerFactory.getInstance(suffix);
				if(fileHandler != null)fileHandler.handle(sb.toString());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	
	public static Properties parseJSON(String fileContent) {
		JSONParser parser = new JSONParser();
		Properties prop = new Properties();
		
		try {
			Object obj = parser.parse(fileContent);

			JSONObject jsonObject = (JSONObject) obj;

			
			if(jsonObject.entrySet() != null) {
				System.out.println(jsonObject.entrySet());
				Set<Entry<String,Object>> entries = jsonObject.entrySet();
				for(Entry entry: entries) {
					System.out.println(entry.getKey() + " " + entry.getValue());
					prop.put(entry.getKey(), entry.getValue());
				}
			}
			
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
