package com.myretail.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.product.dao.ProductPricingDAO;
import com.myretail.product.domain.Price;
import com.myretail.product.domain.ProductDetails;
import com.myretail.product.model.ProductPricing;
import com.myretail.product.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductPricingDAO productPricingDAO;

	@RequestMapping(value = "/products/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDetails getProduct(@PathVariable(value = "id") Long id) {

		ProductDetails productDetails = productService.getProduct(id);

		ProductPricing productPricing = productPricingDAO.retrieveProduct(id);
		if (productPricing != null) {
			Price price = new Price(productPricing.getPrice(), productPricing.getCurrencyCode());
			productDetails.setCurrentPrice(price);
		}
		return productDetails;
	}
	
	@RequestMapping(value = "/products/",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDetails getProduct(@RequestBody ProductDetails productDetails) {

	

		ProductDetails updatedProductDetails = productPricingDAO.updateProduct(productDetails);
		
		return  updatedProductDetails;
	}
}
