package com.spring.dto;

public class CustomVO {
	private Long quantity;
	private Long price;
	private String image;
	private Long productNumber;
	private Long userNumber;
	private Long customNumber;
	private String size;
	private String color;
	private String product;
	

	@Override
	public String toString() {
		return "CustomVO [quantity=" + quantity + ", price=" + price + ", image=" + image + ", productNumber="
				+ productNumber + ", userNumber=" + userNumber + ", customNumber=" + customNumber + ", size=" + size
				+ ", color=" + color + ", product=" + product + "]";
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
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
}
