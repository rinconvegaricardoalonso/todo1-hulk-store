package test.java.com.todo1.hulkstore.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;
import main.java.com.todo1.hulkstore.util.CsvPersistenceManager;

public class CsvPersistenceManagerTest {
	
	private static Logger logger = Logger.getLogger(CsvPersistenceManagerTest.class);
	
	@Before
	public void before() {
		String logConfigPath = "resources\\log.properties";
		PropertyConfigurator.configure(logConfigPath);
	}

	@Test
	public void testListRecords() {
		try {
			String csvNameModel = "product";
			List<String> listColumnNames = new ArrayList<>();
			String id = "1";
			
			listColumnNames.add("id");
			listColumnNames.add("name");
			listColumnNames.add("price");
			listColumnNames.add("quantity");
		
			assertEquals("Successful test", CsvPersistenceManager.listRecords(csvNameModel, listColumnNames).stream().filter(map -> map.get("id").equals(id)).findFirst().map(map -> map.get("id")).get(), id);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testFindRecordById() {
		try {
			String csvNameModel = "product";
			List<String> listColumnNames = new ArrayList<>();
			String id = "1";
			
			listColumnNames.add("id");
			listColumnNames.add("name");
			listColumnNames.add("price");
			listColumnNames.add("quantity");
		
			assertEquals("Successful test", CsvPersistenceManager.findRecordById(csvNameModel, listColumnNames, Long.valueOf(id)).get("id"), id);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testFindRecordByName() {
		try {
			String csvNameModel = "product";
			List<String> listColumnNames = new ArrayList<>();
			String name = "Shoes";
			
			listColumnNames.add("id");
			listColumnNames.add("name");
			listColumnNames.add("price");
			listColumnNames.add("quantity");
		
			assertEquals("Successful test", CsvPersistenceManager.findRecordByName(csvNameModel, listColumnNames, name).get("name"), name);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testFindRecordsByProperty() {
		try {
			String csvNameModel = "productentry";
			List<String> listColumnNames = new ArrayList<>();
			String property = "product_id";
			String value = "1";
			
			listColumnNames.add("id");
			listColumnNames.add("product_id");
			listColumnNames.add("quantity");
			listColumnNames.add("entrydate");
		
			assertEquals("Successful test", CsvPersistenceManager.findRecordsByProperty(csvNameModel, listColumnNames, property, value).stream().filter(map -> map.get("product_id").equals(value)).findFirst().map(map -> map.get("product_id")).get(), value);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testAddRecord() {
		try {
			String csvNameModel = "product";
			List<String> listColumnNames = new ArrayList<>();
			String name = "name_random_" + new Random().nextInt(99999999);
			
			listColumnNames.add("id");
			listColumnNames.add("name");
			listColumnNames.add("price");
			listColumnNames.add("quantity");
			
			Map<String, String> map = new HashedMap<>();
			map.put("name", name);
			map.put("price", "10000");
			map.put("quantity", "33");
		
			assertEquals("Successful test", CsvPersistenceManager.addRecord(csvNameModel, listColumnNames, map).get("name"), name);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testModifyRecord() {
		try {
			String csvNameModel = "product";
			List<String> listColumnNames = new ArrayList<>();
			String name = "Shoes";
			
			listColumnNames.add("id");
			listColumnNames.add("name");
			listColumnNames.add("price");
			listColumnNames.add("quantity");
			
			Map<String, String> map = new HashedMap<>();
			map.put("name", name);
			map.put("price", "120000");
			map.put("quantity", "20");
			
			assertEquals("Successful test", CsvPersistenceManager.modifyRecord(csvNameModel, listColumnNames, map).get("name"), name);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

}
