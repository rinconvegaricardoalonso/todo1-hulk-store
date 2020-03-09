package main.java.com.todo1.hulkstore.dao;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;
import main.java.com.todo1.hulkstore.model.ProductEntry;

public class ProductEntryDAO extends CrudDAOImpl<ProductEntry> {
	
	public ProductEntry addEntriesToProduct(ProductEntry productEntry) throws HulkStoreException {
		return create(productEntry);
	}

}
