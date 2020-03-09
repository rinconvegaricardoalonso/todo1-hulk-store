package main.java.com.todo1.hulkstore.dao;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;
import main.java.com.todo1.hulkstore.model.ProductOutput;

public class ProductOutputDAO extends CrudDAOImpl<ProductOutput> {
	
	public ProductOutput addOutputsToProduct(ProductOutput productEntry) throws HulkStoreException {
		return create(productEntry);
	}

}
