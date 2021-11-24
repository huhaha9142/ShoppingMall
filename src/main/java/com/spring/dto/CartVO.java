package com.spring.dto;

public class CartVO {
	private Long quantity;
	private Long usersNumber;
	private Long productsNumber;
	private Long cartNumber;
	private String product;
	private Long price;
	private String title_image;
	@Override
	public String toString() {
		return "CartVO [quantity=" + quantity + ", usersNumber=" + usersNumber + ", productsNumber=" + productsNumber
				+ ", cartNumber=" + cartNumber + ", product=" + product + ", price=" + price + ", title_image="
				+ title_image + "]";
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
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
	public Long getCartNumber() {
		return cartNumber;
	}
	public void setCartNumber(Long cartNumber) {
		this.cartNumber = cartNumber;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getTitle_image() {
		return title_image;
	}
	public void setTitle_image(String title_image) {
		this.title_image = title_image;
	}
	
	
}
