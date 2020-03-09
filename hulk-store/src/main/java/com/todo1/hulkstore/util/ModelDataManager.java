package main.java.com.todo1.hulkstore.util;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;

public class ModelDataManager {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<?> listRegisters(Class clase) throws HulkStoreException {
		try {
			String csvNameModel = getModelName(clase);
			List<Method> listMethod = listMethod(clase);
			List<String> listColumnNames = getColumnNames(clase);
			List<String> listColumnSetters = getColumnSetters(clase);
			List<Object> listObject = new ArrayList<>();
			
			for(Map<String, String> map : CsvPersistenceManager.listRecords(csvNameModel, listColumnNames)) {
				Object object = clase.newInstance();
				
				for(String columnName : listColumnNames) {
					String setter = listColumnSetters.stream().filter(g -> validateColumn(g, columnName)).findFirst().get();
					Class<?> parameterType = listMethod.stream().filter(m -> m.getName().equalsIgnoreCase(setter)).findFirst().map(m -> m.getParameterTypes()).get()[0];
					
					clase.getMethod(setter, parameterType).invoke(object, getCastedValueByParameterTypeAndValue(parameterType, map.get(columnName)));
				}
				
				listObject.add(object);
			}
			
			return listObject;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object findRegisterById(Long id, Class clase) throws HulkStoreException {
		try {
			String csvNameModel = getModelName(clase);
			List<Method> listMethod = listMethod(clase);
			List<String> listColumnNames = getColumnNames(clase);
			List<String> listColumnSetters = getColumnSetters(clase);
			
			Object object = null;
			Map<String, String> map = CsvPersistenceManager.findRecordById(csvNameModel, listColumnNames, id);
			
			if(!map.isEmpty()) {
				object = clase.newInstance();
				
				for(String columnName : listColumnNames) {
					String setter = listColumnSetters.stream().filter(g -> validateColumn(g, columnName)).findFirst().get();
					Class<?> parameterType = listMethod.stream().filter(m -> m.getName().equalsIgnoreCase(setter)).findFirst().map(m -> m.getParameterTypes()).get()[0];
					
					clase.getMethod(setter, parameterType).invoke(object, getCastedValueByParameterTypeAndValue(parameterType, map.get(columnName)));
				}
			}
			
			return object;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object findRegisterByName(String name, Class clase) throws HulkStoreException {
		try {
			String csvNameModel = getModelName(clase);
			List<Method> listMethod = listMethod(clase);
			List<String> listColumnNames = getColumnNames(clase);
			List<String> listColumnSetters = getColumnSetters(clase);
			
			Object object = null;
			Map<String, String> map = CsvPersistenceManager.findRecordByName(csvNameModel, listColumnNames, name);
			
			if(!map.isEmpty()) {
				object = clase.newInstance();
				
				for(String columnName : listColumnNames) {
					String setter = listColumnSetters.stream().filter(g -> validateColumn(g, columnName)).findFirst().get();
					Class<?> parameterType = listMethod.stream().filter(m -> m.getName().equalsIgnoreCase(setter)).findFirst().map(m -> m.getParameterTypes()).get()[0];
					
					clase.getMethod(setter, parameterType).invoke(object, getCastedValueByParameterTypeAndValue(parameterType, map.get(columnName)));
				}
			}
			
			return object;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<?> findRegisterByProperty(String property, String value, Class clase) throws HulkStoreException {
		try {
			String csvNameModel = getModelName(clase);
			List<Method> listMethod = listMethod(clase);
			List<String> listColumnNames = getColumnNames(clase);
			List<String> listColumnSetters = getColumnSetters(clase);
			List<Object> listObject = new ArrayList<>();
			
			for(Map<String, String> map : CsvPersistenceManager.findRecordsByProperty(csvNameModel, listColumnNames, property, value)) {
				Object object = clase.newInstance();
				
				for(String columnName : listColumnNames) {
					String setter = listColumnSetters.stream().filter(g -> validateColumn(g, columnName)).findFirst().get();
					Class<?> parameterType = listMethod.stream().filter(m -> m.getName().equalsIgnoreCase(setter)).findFirst().map(m -> m.getParameterTypes()).get()[0];
					
					clase.getMethod(setter, parameterType).invoke(object, getCastedValueByParameterTypeAndValue(parameterType, map.get(columnName)));
				}
				
				listObject.add(object);
			}
			
			return listObject;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	public static Object createRegister(Object object) throws HulkStoreException {
		try {
			String csvNameModel = getModelName(object.getClass());
			List<Method> listMethod = listMethod(object.getClass());
			List<String> listColumnNames = getColumnNames(object.getClass());
			List<String> listColumnGetters = getColumnGetters(object.getClass());
			List<String> listColumnSetters = getColumnSetters(object.getClass());
			Map<String, String> mapToEnter = new HashMap<String, String>();
			
			for(String columnName : listColumnNames) {
				String columnNameAux = columnName;
				String getter = listColumnGetters.stream().filter(g -> validateColumn(g, columnNameAux)).findFirst().get();
				Object value = object.getClass().getMethod(getter).invoke(object);
				
				if(value != null && value.toString().contains(".")) {
					String foreingKey = getModelName(value.getClass()) + "_id";
					value = value.getClass().getMethod("getId").invoke(value);
					columnName = foreingKey;
				} else {
					value = casttingValue(value);
				}
				
				mapToEnter.put(columnName, value != null ? value.toString() : null);
			}
			
			Map<String, String> map = CsvPersistenceManager.addRecord(csvNameModel, listColumnNames, mapToEnter);
			
			if(!map.isEmpty()) {
				for(String columnName : listColumnNames) {
					String setter = listColumnSetters.stream().filter(g -> validateColumn(g, columnName)).findFirst().get();
					Class<?> parameterType = listMethod.stream().filter(m -> m.getName().equalsIgnoreCase(setter)).findFirst().map(m -> m.getParameterTypes()).get()[0];
					
					if(map.containsKey(columnName)) {
						object.getClass().getMethod(setter, parameterType).invoke(object, getCastedValueByParameterTypeAndValue(parameterType, map.get(columnName)));
					}
				}
			}
			
			return object;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}

	public static Object editRegister(Object object) throws HulkStoreException {
		try {
			String csvNameModel = getModelName(object.getClass());
			List<Method> listMethod = listMethod(object.getClass());
			List<String> listColumnNames = getColumnNames(object.getClass());
			List<String> listColumnGetters = getColumnGetters(object.getClass());
			List<String> listColumnSetters = getColumnSetters(object.getClass());
			Map<String, String> mapToEnter = new HashMap<String, String>();
			
			for(String columnName : listColumnNames) {
				String columnNameAux = columnName;
				String getter = listColumnGetters.stream().filter(g -> validateColumn(g, columnNameAux)).findFirst().get();
				Object value = object.getClass().getMethod(getter).invoke(object);
				
				if(value != null && value.toString().contains(".")) {
					String foreingKey = getModelName(value.getClass()) + "_id";
					value = value.getClass().getMethod("getId").invoke(value);
					columnName = foreingKey;
				} else {
					value = casttingValue(value);
				}
				
				mapToEnter.put(columnName, value != null ? value.toString() : null);
			}
			
			Map<String, String> map = CsvPersistenceManager.modifyRecord(csvNameModel, listColumnNames, mapToEnter);
			
			if(!map.isEmpty()) {
				for(String columnName : listColumnNames) {
					String setter = listColumnSetters.stream().filter(g -> validateColumn(g, columnName)).findFirst().get();
					Class<?> parameterType = listMethod.stream().filter(m -> m.getName().equalsIgnoreCase(setter)).findFirst().map(m -> m.getParameterTypes()).get()[0];
					
					object.getClass().getMethod(setter, parameterType).invoke(object, getCastedValueByParameterTypeAndValue(parameterType, map.get(columnName)));
				}
			}
			
			return object;
		} catch (Exception e) {
			throw new HulkStoreException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static String getModelName(Class clase) {
		String classToString = clase.toString().replace(".", ";");
		return classToString.split(";")[classToString.split(";").length -1].toLowerCase();
	}
	
	@SuppressWarnings("rawtypes")
	public static List<String> getColumnNames(Class clase) {
		List<Method> listMethod = listMethod(clase);
		
		return listMethod.stream().filter(m -> Boolean.TRUE.equals(validateColumnGetters(m.getName()))).map(m -> getColumn(m.getName())).collect(Collectors.toList());
	}
	
	@SuppressWarnings("rawtypes")
	public static List<String> getColumnGetters(Class clase) {
		List<Method> listMethod = listMethod(clase);
		
		return listMethod.stream().filter(m -> Boolean.TRUE.equals(validateColumnGetters(m.getName()))).map(m -> m.getName()).collect(Collectors.toList());
	}
	
	@SuppressWarnings("rawtypes")
	public static List<String> getColumnSetters(Class clase) {
		List<Method> listMethod = listMethod(clase);
		
		return listMethod.stream().filter(m -> Boolean.TRUE.equals(validateColumnSetters(m.getName()))).map(m -> m.getName()).collect(Collectors.toList());
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Method> listMethod(Class clase) {
		Method[] arrayMethod = clase.getMethods();
		List<Method> listMethod = new ArrayList<>();
		
		Collections.addAll(listMethod, arrayMethod);
		
		return listMethod;
	}
	
	public static Boolean validateColumnGetters(String nameMethod) {
		String operation = nameMethod.substring(0, 3);
		
		if(operation.equalsIgnoreCase("get") && !nameMethod.substring(3, nameMethod.length()).equalsIgnoreCase("Class")) {
			return true;
		}
		
		return false;
	}
	
	public static Boolean validateColumnSetters(String nameMethod) {
		String operation = nameMethod.substring(0, 3);
		
		if(operation.equalsIgnoreCase("set")) {
			return true;
		}
		
		return false;
	}
	
	public static String getColumn(String nameMethod) {
		nameMethod = nameMethod.substring(3, nameMethod.length());
		nameMethod = nameMethod.toLowerCase();
		
		return nameMethod;
	}
	
	public static Boolean validateColumn(String nameMethod, String columnName) {
		String column = nameMethod.substring(3, nameMethod.length()).toLowerCase();
		
		if(columnName.equalsIgnoreCase(column)) {
			return true;
		}
		
		return false;
	}
	
	public static String casttingValue(Object object) {
		if(object != null) {
			if(object instanceof Date) {
				return (new SimpleDateFormat("yyyy-MM-dd")).format(object);
			}
			
			return object.toString();
		}
		
		return "";
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getCastedValueByParameterTypeAndValue(Class parameterType, String value) throws ParseException {
		if(value != null) {
			if(parameterType.equals(Long.class)) {
				return Long.valueOf(value);
			} else if(parameterType.equals(Integer.class)) {
				return Integer.valueOf(value);
			} else if(parameterType.equals(String.class)) {
				return value.toString();
			} else if(parameterType.equals(Date.class)) {
				return new SimpleDateFormat("yyyy-MM-dd").parse(value);
			} 
		}
		
		return null;
	}

}
