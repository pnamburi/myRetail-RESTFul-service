package com.myretail.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price {
	private double value;
	@JsonProperty("currency_code")
	private String currencyCode;

	public Price(double value, String currencyCode) {
		super();
		this.value = value;
		this.currencyCode = currencyCode;
	}

	public Price() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getValue() {
		return value;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
