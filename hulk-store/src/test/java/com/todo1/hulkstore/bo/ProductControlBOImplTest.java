package test.java.com.todo1.hulkstore.bo;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import main.java.com.todo1.hulkstore.bo.ProductControlBOImpl;
import main.java.com.todo1.hulkstore.exception.HulkStoreException;
import main.java.com.todo1.hulkstore.model.Product;
import main.java.com.todo1.hulkstore.model.ProductEntry;
import main.java.com.todo1.hulkstore.model.ProductOutput;

public class ProductControlBOImplTest {
	
private static Logger logger = Logger.getLogger(ProductControlBOImplTest.class);

	private ProductControlBOImpl productControlBOImpl;
	
	@Before
	public void before() {
		String logConfigPath = "resources\\log.properties";
		PropertyConfigurator.configure(logConfigPath);
		productControlBOImpl = new ProductControlBOImpl();
	}

	@Test
	public void testCheckProducts() {
		try {
			Long id = 1l;
			
			assertEquals("Successful test", productControlBOImpl.checkProducts().stream().filter(product -> product.getId().equals(id)).findFirst().map(product -> product.getId()).get(), id);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testSearchProductByName() {
		try {
			String name = "Shoes";
			
			assertEquals("Successful test", productControlBOImpl.searchProductByName(name).getName(), name);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testCreateProduct() {
		try {
			String name = "name_random_" + new Random().nextInt(99999999);
			Product product = new Product();
			product.setName(name);
			product.setPrice(1000);
			product.setQuantity(10);
			
			assertEquals("Successful test", productControlBOImpl.createProduct(product).getName(), name);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testEditProduct() {
		try {
			Product product = productControlBOImpl.checkProducts().stream().findFirst().get();
			product.setQuantity(35);
			String name = product.getName();
			
			assertEquals("Successful test", productControlBOImpl.editProduct(product).getName(), name);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testAddEntriesToProduct() {
		try {
			Product product = productControlBOImpl.checkProducts().stream().findFirst().get();
			String name = product.getName();
			
			ProductEntry productEntry = new ProductEntry();
			productEntry.setEntryDate(Calendar.getInstance().getTime());
			productEntry.setProduct(product);
			productEntry.setQuantity(1);
			
			assertEquals("Successful test", productControlBOImpl.addEntriesToProduct(productEntry).getProduct().getName(), name);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testAddOutputsToProduct() {
		try {
			Product product = productControlBOImpl.checkProducts().stream().findFirst().get();
			String name = product.getName();
			
			ProductOutput productOutput = new ProductOutput();
			productOutput.setDepartureDate(Calendar.getInstance().getTime());
			productOutput.setProduct(product);
			productOutput.setQuantity(1);
			
			assertEquals("Successful test", productControlBOImpl.addOutputsToProduct(productOutput).getProduct().getName(), name);
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
		}
	}

}
