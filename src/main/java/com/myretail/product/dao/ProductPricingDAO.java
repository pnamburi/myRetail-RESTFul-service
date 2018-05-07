package com.myretail.product.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.myretail.product.domain.ProductDetails;
import com.myretail.product.model.ProductPricing;

@Repository
public class ProductPricingDAO {


	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	static String tableName = "product_pricing";

	public ProductPricing retrieveProduct(long id) {

		ProductPricing productPricing = dynamoDBMapper.load(ProductPricing.class, id);

		return productPricing;
	}



	public ProductDetails updateProduct(ProductDetails productDetails) {
		ProductPricing productPricing = new ProductPricing();
		if (productDetails != null
				&& productDetails.getCurrentPrice() != null & productDetails.getCurrentPrice().getValue() > 0 ) {
			productPricing.setCurrencyCode(productDetails.getCurrentPrice().getCurrencyCode());
			productPricing.setProductId(productDetails.getId());
			productPricing.setPrice(productDetails.getCurrentPrice().getValue());
			dynamoDBMapper.save(productPricing);
		}
	
		return productDetails;
	}

}