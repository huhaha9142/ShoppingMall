package com.spring.dto;

import java.util.Date;

public class ReviewVO {
	private String content;
	private String title;
	private String image;
	private Date inDate;
	private Long hit;
	private	Long usersNumber;
	private Long productsNumber;
	private Long reviewsNumber;
	private String product;
	private String id;
	private String name;
	
	@Override
	public String toString() {
		return "ReviewVO [content=" + content + ", title=" + title + ", image=" + image + ", inDate=" + inDate
				+ ", hit=" + hit + ", usersNumber=" + usersNumber + ", productsNumber=" + productsNumber
				+ ", reviewsNumber=" + reviewsNumber + ", product=" + product + ", id=" + id + ", name=" + name + "]";
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ReviewVO() {}
	
	public ReviewVO(String content,String title, String image, Date inDate,
			Long hit, Long usersNumber, Long productsNumber)
	{
		setContent(content);
		setTitle(title);
		setImage(image);
		setInDate(inDate);
		setHit(hit);
		setUsersNumber(usersNumber);
		setProductsNumber(productsNumber); 
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
	public Long getReviewsNumber() {
		return reviewsNumber;
	}
	public void setReviewsNumber(Long reviewsNumber) {
		this.reviewsNumber = reviewsNumber;
	}
	
	
	

	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
}
