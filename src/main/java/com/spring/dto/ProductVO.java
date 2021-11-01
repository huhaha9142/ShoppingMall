package com.spring.dto;

import java.util.Date;

public class ProductVO {
	private Long size;
	private String color;
	private String kind;
	private Long quqntity;
	private Long price;
	private String content;
	private String imageSmall;
	private String imageLazy;
	private String productImage;
	private String product;
	private Long productNumber;
	private Date regDate;
	
	public ProductVO() {
		
	}
	
	public ProductVO(Long size, String color,String kind, Long price,String product)
	{
		this.size =size;
		this.color=color;
		this.kind=kind;
		this.price=price;
		this.product=product;
	}
	
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "ProductVO [size=" + size + ", color=" + color + ", kind=" + kind + ", quqntity=" + quqntity + ", price="
				+ price + ", content=" + content + ", imageSmall=" + imageSmall + ", imageLazy=" + imageLazy
				+ ", productImage=" + productImage + ", product=" + product + ", productNumber=" + productNumber
				+ ", regDate=" + regDate + "]";
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public Long getQuqntity() {
		return quqntity;
	}
	public void setQuqntity(Long quqntity) {
		this.quqntity = quqntity;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageSmall() {
		return imageSmall;
	}
	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}
	public String getImageLazy() {
		return imageLazy;
	}
	public void setImageLazy(String imageLazy) {
		this.imageLazy = imageLazy;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Long getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(Long productNumber) {
		this.productNumber = productNumber;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	
}
