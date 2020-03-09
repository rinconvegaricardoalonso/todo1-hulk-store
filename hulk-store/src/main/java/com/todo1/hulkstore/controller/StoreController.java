package main.java.com.todo1.hulkstore.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import main.java.com.todo1.hulkstore.bo.ProductControlBOImpl;
import main.java.com.todo1.hulkstore.exception.HulkStoreException;
import main.java.com.todo1.hulkstore.model.Product;
import main.java.com.todo1.hulkstore.model.ProductEntry;
import main.java.com.todo1.hulkstore.model.ProductOutput;
import main.java.com.todo1.hulkstore.model.dto.MovementsDTO;
import main.java.com.todo1.hulkstore.view.PrincipalUI;

public class StoreController {
	
	private static Logger logger = Logger.getLogger(StoreController.class);
	
	private ProductControlBOImpl productControlBOImpl;
	private PrincipalUI proncipalUI;
	
	public StoreController() {
		productControlBOImpl = new ProductControlBOImpl();
	}
	
	public List<Product> checkProducts() throws HulkStoreException {
		return productControlBOImpl.checkProducts();
	}
	
	public void searchProductByName(String name) {
		try {
			Product productFound = productControlBOImpl.searchProductByName(name);
			
			if(productFound != null) {
				getProncipalUI().getPanel_3().setVisible(true);
				getProncipalUI().getTxtProduct().setText(productFound.getName());
				getProncipalUI().getTxtPrice().setText(productFound.getPrice().toString());
				getProncipalUI().getTxtQuantity().setText(productFound.getQuantity().toString());
				getProncipalUI().setObjProduct(productFound);
				
				updateGridMovements(productFound);
			} else {
				getProncipalUI().setSize(416, 227);
				cleanProductFields();
				showWarningOrErrorMessage("Attention; The product is not in inventory");
			}
			
			getProncipalUI().getTxtQuantityModify().setText("0");
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
			showWarningOrErrorMessage(e.getMessage());
		}
	}
	
	public void createProduct() {
		try {
			String name = getProncipalUI().getTxtProduct().getText();
			String price = getProncipalUI().getTxtPrice().getText();
			
			if(name.isEmpty()) {
				showMessage("You must type a product to create it");
				return;
			}
			
			if(price.isEmpty()) {
				showMessage("You must type the price of the product");
				return;
			} else if(!price.matches("[0-9]*")) {
				
				showMessage("The price must be numerical, must not contain points or commas");
				return;
			}
			
			Product product = new Product();
			product.setName(name);
			product.setPrice(Integer.valueOf(price));
			product.setQuantity(0);
			
			Product productCreated = productControlBOImpl.createProduct(product);
			
			if(productCreated != null) {
				getProncipalUI().setObjProduct(productCreated);
				showMessage("Product created successfully");
			}
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
			showWarningOrErrorMessage(e.getMessage());
		}
	}
	
	public void editProduct() {
		try {
			Product product = getProncipalUI().getObjProduct();
			
			if(product == null) {
				showMessage("First type the product above and click on the search option to get all the product information");
				return;
			}
			
			String name = getProncipalUI().getTxtProduct().getText();
			String price = getProncipalUI().getTxtPrice().getText();
			
			if(name.isEmpty()) {
				showMessage("You must type a product to modify it");
				return;
			}
			
			if(price.isEmpty()) {
				showMessage("You must type the price of the product");
				return;
			} else if(!price.matches("[0-9]*")) {
				showMessage("The price must be numerical, must not contain points or commas");
				return;
			}
			
			product.setName(name);
			product.setPrice(Integer.valueOf(price));
			product.setQuantity(product.getQuantity() != null ? product.getQuantity() : 0);
			
			Product modifiedProduct = productControlBOImpl.editProduct(product);
			
			if(modifiedProduct != null) {
				getProncipalUI().setObjProduct(modifiedProduct);
				showMessage("Product modified successfully");
			}
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
			showWarningOrErrorMessage(e.getMessage());
		}
	}
	
	public void addEntriesToProduct() {
		try {
			Product product = getProncipalUI().getObjProduct();
			
			if(product == null) {
				showMessage("First type the product above and click on the search option to get all the product information");
				return;
			}
			
			String quantity = getProncipalUI().getTxtQuantityModify().getText().trim();
			
			if(quantity.isEmpty()) {
				showMessage("You must type the amount to add to the product");
				return;
			} else if(!quantity.matches("[0-9]*")) {
				showMessage("The amount must be numerical");
				return;
			} else if(quantity.equals("0")) {
				showMessage("No products were added to this!");
				return;
			}
			
			ProductEntry productEntry = new ProductEntry();
			productEntry.setProduct(product);
			productEntry.setQuantity(Integer.valueOf(quantity));
			productEntry.setEntryDate(Calendar.getInstance().getTime());
			
			ProductEntry productEntryAdded = productControlBOImpl.addEntriesToProduct(productEntry);
			
			if(productEntryAdded != null) {
				getProncipalUI().getTxtQuantity().setText(productEntryAdded.getProduct().getQuantity().toString());
				getProncipalUI().getTxtQuantityModify().setText("0");
				getProncipalUI().setObjProduct(productEntryAdded.getProduct());
				updateGridMovements(productEntryAdded.getProduct());
				showMessage("Product modified successfully");
			}
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
			showWarningOrErrorMessage(e.getMessage());
		}
	}
	
	public void addOutputsToProduct() {
		try {
			Product product = getProncipalUI().getObjProduct();
			
			if(product == null) {
				showMessage("First type the product above and click on the search option to get all the product information");
				return;
			}
			
			String quantity = getProncipalUI().getTxtQuantityModify().getText().trim();
			
			if(quantity.isEmpty()) {
				showMessage("You must type the amount to remove to the product");
				return;
			} else if(!quantity.matches("[0-9]*")) {
				showMessage("The amount must be numerical");
				return;
			} else if(quantity.equals("0")) {
				showMessage("No products were removed from this!");
				return;
			}
			
			ProductOutput productOutput = new ProductOutput();
			productOutput.setProduct(product);
			productOutput.setQuantity(Integer.valueOf(quantity));
			productOutput.setDepartureDate(Calendar.getInstance().getTime());
			
			ProductOutput productOutputAdded = productControlBOImpl.addOutputsToProduct(productOutput);
			
			if(productOutputAdded != null) {
				getProncipalUI().getTxtQuantity().setText(productOutputAdded.getProduct().getQuantity().toString());
				getProncipalUI().getTxtQuantityModify().setText("0");
				getProncipalUI().setObjProduct(productOutputAdded.getProduct());
				updateGridMovements(productOutputAdded.getProduct());
				
				showMessage("Product modified successfully");
			}
		} catch (HulkStoreException e) {
			logger.info(e.getMessage(), e);
			showWarningOrErrorMessage(e.getMessage());
		}
	}

	/**
	 * @return the proncipalUI
	 */
	public PrincipalUI getProncipalUI() {
		return proncipalUI;
	}

	/**
	 * @param proncipalUI the proncipalUI to set
	 */
	public void setProncipalUI(PrincipalUI proncipalUI) {
		this.proncipalUI = proncipalUI;
	}
	
	public void cleanAllfields() {
		getProncipalUI().getTxtProductSearch().setText("");
		cleanProductFields();
		getProncipalUI().getTxtQuantityModify().setText("0");
		getProncipalUI().setObjProduct(null);
		getProncipalUI().getPanel_3().setVisible(false);
		getProncipalUI().setSize(416, 227);
	}

	private void cleanProductFields() {
		getProncipalUI().getTxtProduct().setText("");
		getProncipalUI().getTxtPrice().setText("");
		getProncipalUI().getTxtQuantity().setText("0");
	}
	
	private void showMessage(String message) {
		JOptionPane.showMessageDialog(getProncipalUI(), message);
	}
	
	private void showWarningOrErrorMessage(String message) {
		if(!message.contains("Attention;")) message = "Unexpected error has ocurred";
		
		showMessage(message);
	}
	
	private void updateGridMovements(Product product) throws HulkStoreException {
		List<MovementsDTO> listMovements = new ArrayList<>();
		
		List<ProductEntry> listProductEntry = productControlBOImpl.checkProductEntryByProduct(product);
		for(ProductEntry productEntry : listProductEntry) {
			listMovements.add(new MovementsDTO(productEntry.getId(), getProncipalUI().getTxtProduct().getText(), productEntry.getQuantity(), productEntry.getEntryDate(), "Added"));
		}
		
		List<ProductOutput> listProductOutput = productControlBOImpl.checkProductOutputByProduct(product);
		for(ProductOutput productOutput : listProductOutput) {
			listMovements.add(new MovementsDTO(productOutput.getId(), getProncipalUI().getTxtProduct().getText(), productOutput.getQuantity(), productOutput.getDepartureDate(), "Removed"));
		}
		
		if(listMovements.isEmpty()) {
			getProncipalUI().getPanel_3().setVisible(false);
			getProncipalUI().setSize(416, 227);
			return;
		}
		
		getProncipalUI().setSize(416, 328);
		getProncipalUI().buildGrid(listMovements.size(), listMovements);
	}
	
}
