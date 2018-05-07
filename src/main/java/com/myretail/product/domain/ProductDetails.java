package com.myretail.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ProductDetails {

	private long id;
	private String name;
	@JsonProperty("current_price")
	private Price currentPrice;

	public ProductDetails(long id) {
		super();
		this.id = id;
	}

	public ProductDetails() {
		super();	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Price getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Price currentPrice) {
		this.currentPrice = currentPrice;
	}

	public long getId() {
		return id;
	}

	public ProductDetails(long id, String name, Price currentPrice) {
		super();
		this.id = id;
		this.name = name;
		this.currentPrice = currentPrice;
	}

	

}
