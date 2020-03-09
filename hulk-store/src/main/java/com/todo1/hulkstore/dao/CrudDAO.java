package main.java.com.todo1.hulkstore.dao;

import java.util.List;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;

public interface CrudDAO<T> {
	
	@SuppressWarnings("rawtypes")
	public List<T> list(Class clase) throws HulkStoreException;
	
	@SuppressWarnings("rawtypes")
	public T find(Long id, Class clase) throws HulkStoreException;
	
	@SuppressWarnings("rawtypes")
	public T findByName(String name, Class clase) throws HulkStoreException;
	
	@SuppressWarnings("rawtypes")
	public List<T> findByProperty(String property, String value, Class clase) throws HulkStoreException;
	
	public T create(T object) throws HulkStoreException;
	
	public T edit(T object) throws HulkStoreException;
	
	public void delete(T object) throws HulkStoreException;

}
