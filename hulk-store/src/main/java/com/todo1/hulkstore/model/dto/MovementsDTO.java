package main.java.com.todo1.hulkstore.model.dto;

import java.util.Date;

import main.java.com.todo1.hulkstore.model.Product;

public class MovementsDTO {
	
	private Long id;
	private String product;
	private Integer quantity;
	private Date date;
	private String movement;
	
	public MovementsDTO(Long id, String product, Integer quantity, Date date, String movement) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.date = date;
		this.movement = movement;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the movement
	 */
	public String getMovement() {
		return movement;
	}

	/**
	 * @param movement the movement to set
	 */
	public void setMovement(String movement) {
		this.movement = movement;
	}

}
