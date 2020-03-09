package main.java.com.todo1.hulkstore.bo;

import java.util.List;

import main.java.com.todo1.hulkstore.dao.ProductDAO;
import main.java.com.todo1.hulkstore.dao.ProductEntryDAO;
import main.java.com.todo1.hulkstore.dao.ProductOutputDAO;
import main.java.com.todo1.hulkstore.exception.HulkStoreException;
import main.java.com.todo1.hulkstore.model.Product;
import main.java.com.todo1.hulkstore.model.ProductEntry;
import main.java.com.todo1.hulkstore.model.ProductOutput;

public class ProductControlBOImpl implements ProductControlBO {
	
	private ProductDAO productDAO;
	private ProductEntryDAO productEntryDAO;
	private ProductOutputDAO productOutputDAO;
	
	public ProductControlBOImpl() {
		productDAO = new ProductDAO();
		productEntryDAO = new ProductEntryDAO();
		productOutputDAO = new ProductOutputDAO();
	}

	@Override
	public List<Product> checkProducts() throws HulkStoreException {
		return productDAO.list(Product.class);
	}
	
	@Override
	public Product searchProductByName(String name) throws HulkStoreException {
		return productDAO.findByName(name, Product.class);
	}

	@Override
	public Product createProduct(Product product) throws HulkStoreException {
		if(productDAO.findByName(product.getName(), Product.class) != null) {
			throw new HulkStoreException("Attention; The '" + product.getName() + "' product already exists in the inventory");
		}
		
		return productDAO.saveProduct(product);
	}

	@Override
	public Product editProduct(Product product) throws HulkStoreException {
		Product productFound = productDAO.findByName(product.getName(), Product.class);
		
		if(productFound != null && !productFound.getId().equals(product.getId())) {
			throw new HulkStoreException("Attention; The product for which you want to modify already exists in the inventory");
		}
		
		return productDAO.saveProduct(product);
	}

	@Override
	public ProductEntry addEntriesToProduct(ProductEntry productEntry) throws HulkStoreException {
		productEntry.getProduct().setQuantity(productEntry.getProduct().getQuantity() + productEntry.getQuantity());
		productDAO.saveProduct(productEntry.getProduct());
		
		return productEntryDAO.addEntriesToProduct(productEntry);
	}

	@Override
	public ProductOutput addOutputsToProduct(ProductOutput productOutput) throws HulkStoreException {
		if(productOutput.getProduct().getQuantity() == 0) {
			throw new HulkStoreException("Attention; Product has no stock available");
		} else if(productOutput.getProduct().getQuantity() < productOutput.getQuantity()) {
			throw new HulkStoreException("Attention; Cannot remove more than the amount available in stock; Available in stock: " + productOutput.getProduct().getQuantity());
		}
		
		productOutput.getProduct().setQuantity(productOutput.getProduct().getQuantity() - productOutput.getQuantity());
		productDAO.saveProduct(productOutput.getProduct());
		
		return productOutputDAO.addOutputsToProduct(productOutput);
	}

	@Override
	public List<ProductEntry> checkProductEntryByProduct(Product product) throws HulkStoreException {
		return productEntryDAO.findByProperty("product_id", product.getId().toString(), ProductEntry.class);
	}

	@Override
	public List<ProductOutput> checkProductOutputByProduct(Product product) throws HulkStoreException {
		return productOutputDAO.findByProperty("product_id", product.getId().toString(), ProductOutput.class);
	}

}
