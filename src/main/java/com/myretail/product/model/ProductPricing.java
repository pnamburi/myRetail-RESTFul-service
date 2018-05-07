package com.myretail.product.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "product_pricing")
public class ProductPricing {

	private Long productId;
	private Double price;
	private String currencyCode;

	@DynamoDBHashKey(attributeName="product_id")
	public Long getProductId() {
		return productId;
	}

	@DynamoDBAttribute
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@DynamoDBAttribute(attributeName="currency_code")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ProductPricing(Long productId, Double price, String currencyCode) {
		super();
		this.productId = productId;
		this.price = price;
		this.currencyCode = currencyCode;
	}

	public ProductPricing() {
		super();
	}

}
