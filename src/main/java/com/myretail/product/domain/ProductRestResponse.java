package com.myretail.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ProductRestResponse {
private Product product;
@JsonIgnoreProperties(ignoreUnknown = true)
public Product getProduct() {
	return product;
}

public void setProduct(Product product) {
	this.product = product;
}

}
