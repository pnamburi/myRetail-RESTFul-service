package com.myretail.product.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.myretail.product.domain.ProductDetails;
import com.myretail.product.model.ProductPricing;
/**
 * This calss is used for Amazon DynamDB database interacttions 
 * @author pnamb
 *
 */
@Repository
public class ProductPricingDAO {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	static String tableName = "product_pricing";

	/**
	 * Gets product pricing information from Amazon DynamoDB NoSQL database.
	 * 
	 * @param productId
	 *            Id of product .
	 * 
	 * @return ProductPricing object with product pricing data
	 */
	public ProductPricing retrieveProduct(long id) {

		ProductPricing productPricing = dynamoDBMapper.load(ProductPricing.class, id);

		return productPricing;
	}

	/**
	 * Update product pricing information in Amazon DynamoDB NoSQL database.
	 * 
	 * @param productDetails,
	 *            Product Details to update
	 */

	public void updateProduct(ProductDetails productDetails) {
		ProductPricing productPricing = new ProductPricing();
		if (productDetails != null
				&& productDetails.getCurrentPrice() != null & productDetails.getCurrentPrice().getValue() > 0) {
			productPricing.setCurrencyCode(productDetails.getCurrentPrice().getCurrencyCode());
			productPricing.setProductId(productDetails.getId());
			productPricing.setPrice(productDetails.getCurrentPrice().getValue());
			dynamoDBMapper.save(productPricing);
		}

	}

}