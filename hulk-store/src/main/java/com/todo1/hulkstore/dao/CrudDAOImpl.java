package main.java.com.todo1.hulkstore.dao;

import java.util.List;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;
import main.java.com.todo1.hulkstore.util.ModelDataManager;

public class CrudDAOImpl<T> implements CrudDAO<T> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> list(Class clase) throws HulkStoreException {
		return (List<T>) ModelDataManager.listRegisters(clase);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T find(Long id, Class clase) throws HulkStoreException {
		return (T) ModelDataManager.findRegisterById(id, clase);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T findByName(String name, Class clase) throws HulkStoreException {
		return (T) ModelDataManager.findRegisterByName(name, clase);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> findByProperty(String property, String value, Class clase) throws HulkStoreException {
		return (List<T>) ModelDataManager.findRegisterByProperty(property, value, clase);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T create(T object) throws HulkStoreException {
		return (T) ModelDataManager.createRegister(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T edit(T object) throws HulkStoreException {
		return (T) ModelDataManager.editRegister(object);
	}

	@Override
	public void delete(T object) {
		// empty method
	}

}
