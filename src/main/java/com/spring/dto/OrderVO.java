package com.spring.dto;

import java.util.Date;

public class OrderVO {
	private Long price;
	private Long quantity;
	private Long result;
	private Long usersNumber;
	private Long productsNumber;
	private Long ordersNumber;
	private Long productCustomNumber;
	private Date inDate;
	private Date regDate;
	private String product; //products Table
	private String size;
	private String color;
	private String titleImage;
	private String customImage;
	
	public Long getProductCustomNumber() {
		return productCustomNumber;
	}
	public void setProductCustomNumber(Long productCustomNumber) {
		this.productCustomNumber = productCustomNumber;
	}
	public String getCustomImage() {
		return customImage;
	}
	public void setCustomImage(String customImage) {
		this.customImage = customImage;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
	}
	public Long getUsersNumber() {
		return usersNumber;
	}
	public void setUsersNumber(Long usersNumber) {
		this.usersNumber = usersNumber;
	}
	public Long getProductsNumber() {
		return productsNumber;
	}
	public void setProductsNumber(Long productsNumber) {
		this.productsNumber = productsNumber;
	}
	public Long getOrdersNumber() {
		return ordersNumber;
	}
	public void setOrdersNumber(Long ordersNumber) {
		this.ordersNumber = ordersNumber;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTitleImage() {
		return titleImage;
	}
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}
	
}
