package main.java.com.todo1.hulkstore.dao;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;
import main.java.com.todo1.hulkstore.model.Product;

public class ProductDAO extends CrudDAOImpl<Product> {
	
	public Product saveProduct(Product product) throws HulkStoreException {
		if(product.getId() == null) {
			return create(product);
		} else {
			return edit(product);
		}
	}

}
