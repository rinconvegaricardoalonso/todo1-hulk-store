package main.java.com.todo1.hulkstore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;

public class DataProperties {
	private static Logger logger = Logger.getLogger(DataProperties.class);
	private static Properties properties = new Properties();
	
	private static DataProperties instanceDataProperties = null;
	
	private DataProperties() throws HulkStoreException {
		try {
			File file = new File("resources\\properties");
	
			InputStream fileInputStream;
	
			if (file.exists()) {
				fileInputStream = new FileInputStream(file);
			} else {
				fileInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("properties");
	
				if (fileInputStream == null) {
					throw new HulkStoreException("Properties file not found");
				}
			}
	
			properties.load(fileInputStream);
		
		} catch (FileNotFoundException e) {
			throw new HulkStoreException("Properties file not found");
		} catch (IOException e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	public static DataProperties getInstance() {
		if (instanceDataProperties == null) {
			try {
				instanceDataProperties = new DataProperties();
			} catch (HulkStoreException e) {
				logger.error(e.getMessage());
			}
		}

		return instanceDataProperties;
	}
	
	public String getdataProperties(String name) {
		return properties.getProperty(name);
	}
}
