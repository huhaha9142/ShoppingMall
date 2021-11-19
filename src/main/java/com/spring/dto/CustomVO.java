package com.spring.dto;

import java.util.Date;

public class CustomVO {

	private String image;
	private Long productNumber;
	private Long userNumber;
	private Long customNumber;
	private String size;
	private String color;
	private String product;
	private Date inDate;
	private Date regDate;
		
	
	@Override
	public String toString() {
		return "CustomVO [image=" + image + ", productNumber=" + productNumber + ", userNumber=" + userNumber
				+ ", customNumber=" + customNumber + ", size=" + size + ", color=" + color + ", product=" + product
				+ ", inDate=" + inDate + ", regDate=" + regDate + "]";
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(Long productNumber) {
		this.productNumber = productNumber;
	}
	public Long getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(Long userNumber) {
		this.userNumber = userNumber;
	}
	public Long getCustomNumber() {
		return customNumber;
	}
	public void setCustomNumber(Long customNumber) {
		this.customNumber = customNumber;
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
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
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
	
	

	
	
}
