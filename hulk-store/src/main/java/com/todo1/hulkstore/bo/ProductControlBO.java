package main.java.com.todo1.hulkstore.bo;

import java.util.List;

import main.java.com.todo1.hulkstore.exception.HulkStoreException;
import main.java.com.todo1.hulkstore.model.Product;
import main.java.com.todo1.hulkstore.model.ProductEntry;
import main.java.com.todo1.hulkstore.model.ProductOutput;

public interface ProductControlBO {
	
	public List<Product> checkProducts() throws HulkStoreException;
	public Product searchProductByName(String name) throws HulkStoreException;
	public Product createProduct(Product product) throws HulkStoreException;
	public Product editProduct(Product product) throws HulkStoreException;
	public ProductEntry addEntriesToProduct(ProductEntry productEntry) throws HulkStoreException;
	public ProductOutput addOutputsToProduct(ProductOutput productOutput) throws HulkStoreException;
	public List<ProductEntry> checkProductEntryByProduct(Product product) throws HulkStoreException;
	public List<ProductOutput> checkProductOutputByProduct(Product product) throws HulkStoreException;

}
