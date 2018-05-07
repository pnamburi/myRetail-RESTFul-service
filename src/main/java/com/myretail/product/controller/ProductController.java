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

/**
 * Spring Controller class that expose the REST APIs for product Details
 * 
 * @author pnamb
 *
 */
@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductPricingDAO productPricingDAO;

	/**
	 * API to get the Product pricing Details.
	 * 
	 * @param Id
	 *            of product.
	 * @return ProductDetails JSON. Example {"id":13860428,"name":"The Big Lebowski
	 *         (Blu-ray) (Widescreen)","current_price":{"value":
	 *         13.49,"currency_code":"USD"}}
	 * 
	 */
	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDetails getProduct(@PathVariable(value = "id") Long id) {

		ProductDetails productDetails = productService.getProduct(id);

		ProductPricing productPricing = productPricingDAO.retrieveProduct(id);
		if (productPricing != null) {
			Price price = new Price(productPricing.getPrice(), productPricing.getCurrencyCode());
			productDetails.setCurrentPrice(price);
		}
		return productDetails;
	}

	/**
	 * API to update the Product pricing Details.
	 * 
	 * @param ProductDetails
	 *            JSON. Example {"id":13860428,"name":"The Big Lebowski (Blu-ray)
	 *            (Widescreen)","current_price":{"value":
	 *            13.49,"currency_code":"USD"}}
	 * 
	 * @return nothing, Spring should take care of returning the success HTTPS code.
	 * 
	 */
	@RequestMapping(value = "/products/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getProduct(@RequestBody ProductDetails productDetails) {

		productPricingDAO.updateProduct(productDetails);

	}
}
