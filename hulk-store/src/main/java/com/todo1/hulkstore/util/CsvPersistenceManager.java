package main.java.com.todo1.hulkstore.util;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jumpmind.symmetric.csv.CsvReader;
import org.jumpmind.symmetric.csv.CsvWriter;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;

public class CsvPersistenceManager {
	
	public static List<Map<String, String>> listRecords(String csvNameModel, List<String> listColumnNames) throws HulkStoreException {
		try {
			List<Map<String, String>> listMap = new ArrayList<>();
			
			CsvReader csvReader = new CsvReader(DataProperties.getInstance().getdataProperties("path_tables") + csvNameModel + ".csv", ';');
			csvReader.readHeaders();
			
			while(csvReader.readRecord()) {
				Map<String, String> map = new HashMap<String, String>();
				
				for(String columnName : listColumnNames) {
					if(!csvReader.get(columnName + "_id").isEmpty()) columnName = columnName + "_id";
					
					map.put(columnName, csvReader.get(columnName));
				}
				
				listMap.add(map);
	        }
			
			csvReader.close();
			
			return listMap;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	public static Map<String, String> findRecordById(String csvNameModel, List<String> listColumnNames, Long id) throws HulkStoreException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			
			CsvReader csvReader = new CsvReader(DataProperties.getInstance().getdataProperties("path_tables") + csvNameModel + ".csv", ';');
			csvReader.readHeaders();
			
			while(csvReader.readRecord()) {
				if(csvReader.get("id").equals(id.toString())) {
					for(String columnName : listColumnNames) {
						if(!csvReader.get(columnName + "_id").isEmpty()) columnName = columnName + "_id";
						
						map.put(columnName, csvReader.get(columnName));
					}
					
					break;
				}
	        }
			
			csvReader.close();
			
			return map;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	public static Map<String, String> findRecordByName(String csvNameModel, List<String> listColumnNames, String name) throws HulkStoreException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			
			CsvReader csvReader = new CsvReader(DataProperties.getInstance().getdataProperties("path_tables") + csvNameModel + ".csv", ';');
			csvReader.readHeaders();
			
			while(csvReader.readRecord()) {
				if(csvReader.get("name").toLowerCase().equals(name.toLowerCase())) {
					for(String columnName : listColumnNames) {
						if(!csvReader.get(columnName + "_id").isEmpty()) columnName = columnName + "_id";
						
						map.put(columnName, csvReader.get(columnName));
					}
					
					break;
				}
	        }
			
			csvReader.close();
			
			return map;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	public static List<Map<String, String>> findRecordsByProperty(String csvNameModel, List<String> listColumnNames, String property, String value) throws HulkStoreException {
		try {
			List<Map<String, String>> listMap = new ArrayList<>();
			
			CsvReader csvReader = new CsvReader(DataProperties.getInstance().getdataProperties("path_tables") + csvNameModel + ".csv", ';');
			csvReader.readHeaders();
			
			while(csvReader.readRecord()) {
				if(csvReader.get(property).toLowerCase().equals(value.toLowerCase())) {
					Map<String, String> map = new HashMap<String, String>();
					
					for(String columnName : listColumnNames) {
						if(!csvReader.get(columnName + "_id").isEmpty()) columnName = columnName + "_id";
						
						map.put(columnName, csvReader.get(columnName));
					}
					
					listMap.add(map);
				}
	        }
			
			csvReader.close();
			
			return listMap;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	public static Map<String, String> addRecord(String csvNameModel, List<String> listColumnNames, Map<String, String> map) throws HulkStoreException {
		try {
			List<Map<String, String>> listMap = listRecords(csvNameModel, listColumnNames);
			
			map.put("id", nextValSequence(csvNameModel));
			listMap.add(map);
			
			CsvWriter csvWriter = new CsvWriter(new FileWriter(DataProperties.getInstance().getdataProperties("path_tables") + csvNameModel + ".csv"), ';');
			
			for(String columnName : listColumnNames) {
				if(map.containsKey(columnName + "_id")) columnName = columnName + "_id";
				
				csvWriter.write(columnName);
			}
			
			csvWriter.endRecord();
			
			for(Map<String, String> mapAux : listMap) {
				for(String columnName : listColumnNames) {
					if(mapAux.containsKey(columnName + "_id")) columnName = columnName + "_id";
					
					csvWriter.write(mapAux.get(columnName));
				}
				
				csvWriter.endRecord();
			}
			
			csvWriter.close();
			
			return map;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	public static Map<String, String> modifyRecord(String csvNameModel, List<String> listColumnNames, Map<String, String> map) throws HulkStoreException {
		try {
			List<Map<String, String>> listMap = listRecords(csvNameModel, listColumnNames);
			
			Map<String, String> mapFound = listMap.stream().filter(mapAux -> mapAux.get("id").equals(map.get("id"))).findFirst().get();
			
			for(String columnName : listColumnNames) {
				mapFound.put(columnName, map.get(columnName));
			}
			
			CsvWriter csvWriter = new CsvWriter(new FileWriter(DataProperties.getInstance().getdataProperties("path_tables") + csvNameModel + ".csv"), ';');
			
			for(String columnName : listColumnNames) {
				csvWriter.write(columnName);
			}
			
			csvWriter.endRecord();
			
			for(Map<String, String> mapAux : listMap) {
				for(String columnName : listColumnNames) {
					if(mapFound.containsKey(columnName + "_id")) columnName = columnName + "_id";
					
					csvWriter.write(mapAux.get(columnName));
				}
				
				csvWriter.endRecord();
			}
			
			csvWriter.close();
			
			return map;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	private static String nextValSequence(String csvNameModel) throws HulkStoreException {
		try {
			CsvReader csvReader = new CsvReader(DataProperties.getInstance().getdataProperties("path_sequences") + csvNameModel + "_seq.csv", ';');
			csvReader.readHeaders();
			csvReader.readRecord();
			Long seq = Long.valueOf(csvReader.get("sequence"));
			seq++;
			csvReader.close();
			
			CsvWriter csvWriter = new CsvWriter(new FileWriter(DataProperties.getInstance().getdataProperties("path_sequences") + csvNameModel + "_seq.csv"), ';');
			csvWriter.write("sequence");
			csvWriter.endRecord();
			csvWriter.write(seq.toString());
			csvWriter.endRecord();
			csvWriter.close();
			
			return seq.toString();
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}

}
