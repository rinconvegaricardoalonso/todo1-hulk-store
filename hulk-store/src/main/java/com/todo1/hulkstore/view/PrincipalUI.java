package main.java.com.todo1.hulkstore.view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import main.java.com.todo1.hulkstore.controller.StoreController;
import main.java.com.todo1.hulkstore.model.Product;
import main.java.com.todo1.hulkstore.model.dto.MovementsDTO;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PrincipalUI extends JFrame {
	private JTextField txtProductSearch;
	private JTextField txtProduct;
	private JTextField txtPrice;
	private JTextField txtQuantity;
	private JTextField txtQuantityModify;
	
	private JPanel panel_3;
	private GridLayout gridMoviments;
	private JPanel panel_5;
	
	private Product objProduct;
	
	private StoreController storeController;
	
	public PrincipalUI() {
		storeController = new StoreController();
		storeController.setProncipalUI(this);
		
		setTitle("Hulk Store");
		setFont(new Font("Arial Black", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(416, 227);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(30, 11, 338, 35);
		getContentPane().add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("60px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("87px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("80px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("80px"),},
			new RowSpec[] {
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));
		
		JLabel lblNewLabel = new JLabel("Product:");
		panel.add(lblNewLabel, "2, 2, left, center");
		
		txtProductSearch = new JTextField();
		panel.add(txtProductSearch, "4, 2, left, center");
		txtProductSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		panel.add(btnSearch, "6, 2, fill, center");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				storeController.searchProductByName(txtProductSearch.getText());
			}
		});
		
		JButton btnClean = new JButton("Clean");
		btnClean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				storeController.cleanAllfields();
			}
		});
		panel.add(btnClean, "8, 2, fill, center");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 69, 187, 113);
		getContentPane().add(panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("86px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("86px:grow"),},
			new RowSpec[] {
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblProduct = new JLabel("Product:");
		panel_1.add(lblProduct, "2, 2, left, center");
		
		txtProduct = new JTextField();
		panel_1.add(txtProduct, "4, 2, left, top");
		txtProduct.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price:");
		panel_1.add(lblPrice, "2, 4, left, default");
		
		txtPrice = new JTextField();
		panel_1.add(txtPrice, "4, 4, left, default");
		txtPrice.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Stock:");
		panel_1.add(lblQuantity, "2, 6, left, default");
		
		txtQuantity = new JTextField("0");
		txtQuantity.setEnabled(false);
		panel_1.add(txtQuantity, "4, 6, left, default");
		txtQuantity.setColumns(10);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				storeController.createProduct();
			}
		});
		panel_1.add(btnCreate, "2, 8, 2, 1, center, default");
		
		JButton btnModify = new JButton("Modify");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				storeController.editProduct();
			}
		});
		panel_1.add(btnModify, "4, 8, center, default");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(225, 69, 175, 60);
		getContentPane().add(panel_2);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("70px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("86px"),},
			new RowSpec[] {
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblQuantityModify = new JLabel("Amount:");
		panel_2.add(lblQuantityModify, "2, 2, left, center");
		
		txtQuantityModify = new JTextField("0");
		panel_2.add(txtQuantityModify, "4, 2, left, top");
		txtQuantityModify.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				storeController.addEntriesToProduct();
			}
		});
		panel_2.add(btnAdd, "2, 4");
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				storeController.addOutputsToProduct();
			}
		});
		panel_2.add(btnRemove, "4, 4, center, default");
		
		panel_3 = new JPanel();
		panel_3.setVisible(false);
		panel_3.setBounds(10, 193, 390, 100);
		getContentPane().add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new GridLayout(1, 4, 0, 0));
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblDate);
		
		JLabel lblAddOrRemove = new JLabel("Movement");
		lblAddOrRemove.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblAddOrRemove);
		
		JLabel lblQuantity_1 = new JLabel("Quantity");
		lblQuantity_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblQuantity_1);
		
		JLabel lblProduct_1 = new JLabel("Product");
		lblProduct_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblProduct_1);
		
		panel_5 = new JPanel();
		panel_5.setAutoscrolls(true);
		panel_3.add(panel_5, BorderLayout.CENTER);
		gridMoviments = new GridLayout(1, 4, 0, 0);
		panel_5.setLayout(gridMoviments);
	}

	/**
	 * @return the txtProductSearch
	 */
	public JTextField getTxtProductSearch() {
		return txtProductSearch;
	}

	/**
	 * @param txtProductSearch the txtProductSearch to set
	 */
	public void setTxtProductSearch(JTextField txtProductSearch) {
		this.txtProductSearch = txtProductSearch;
	}

	/**
	 * @return the txtProduct
	 */
	public JTextField getTxtProduct() {
		return txtProduct;
	}

	/**
	 * @param txtProduct the txtProduct to set
	 */
	public void setTxtProduct(JTextField txtProduct) {
		this.txtProduct = txtProduct;
	}

	/**
	 * @return the txtPrice
	 */
	public JTextField getTxtPrice() {
		return txtPrice;
	}

	/**
	 * @param txtPrice the txtPrice to set
	 */
	public void setTxtPrice(JTextField txtPrice) {
		this.txtPrice = txtPrice;
	}

	/**
	 * @return the txtQuantity
	 */
	public JTextField getTxtQuantity() {
		return txtQuantity;
	}

	/**
	 * @param txtQuantity the txtQuantity to set
	 */
	public void setTxtQuantity(JTextField txtQuantity) {
		this.txtQuantity = txtQuantity;
	}

	/**
	 * @return the txtQuantityModify
	 */
	public JTextField getTxtQuantityModify() {
		return txtQuantityModify;
	}

	/**
	 * @param txtQuantityModify the txtQuantityModify to set
	 */
	public void setTxtQuantityModify(JTextField txtQuantityModify) {
		this.txtQuantityModify = txtQuantityModify;
	}

	/**
	 * @return the objProduct
	 */
	public Product getObjProduct() {
		return objProduct;
	}

	/**
	 * @param objProduct the objProduct to set
	 */
	public void setObjProduct(Product objProduct) {
		this.objProduct = objProduct;
	}

	/**
	 * @return the panel_3
	 */
	public JPanel getPanel_3() {
		return panel_3;
	}

	/**
	 * @param panel_3 the panel_3 to set
	 */
	public void setPanel_3(JPanel panel_3) {
		this.panel_3 = panel_3;
	}
	
	public void buildGrid(int count, List<MovementsDTO> list) {
		panel_5.removeAll();
		gridMoviments = new GridLayout(count, 4, 5, 5);
		panel_5.setLayout(gridMoviments);
		
		for(MovementsDTO movement : list) {
			JLabel lblDate_1 = new JLabel((new SimpleDateFormat("yyyy-MM-dd")).format(movement.getDate()));
			lblDate_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_5.add(lblDate_1);
			
			JLabel lblAddOrRemove_1 = new JLabel(movement.getMovement());
			lblAddOrRemove_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_5.add(lblAddOrRemove_1);
			
			JLabel lblQuantityaux_1 = new JLabel(movement.getQuantity().toString());
			lblQuantityaux_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_5.add(lblQuantityaux_1);
			
			JLabel lblProductaux_1 = new JLabel(movement.getProduct());
			lblProductaux_1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_5.add(lblProductaux_1);
		}
		panel_3.setVisible(false);
		panel_3.setVisible(true);
		panel_5.doLayout();
	}
}
