package com.myretail.product.service;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myretail.product.domain.ProductDetails;
import com.myretail.product.domain.ProductRestResponse;

/**
 * This class is used to interact with the redsky.target.com REST service. This
 * need to be modified when the service hosted internally
 *
 * @author pnamb
 *
 */
@Service
public class ProductService {
	
	Logger logger = Logger.getLogger(ProductService.class);
	
	@Autowired
	RestTemplate restTemplate;

	/**
	 * Get the Product information from redsky.target.com, this is something that
	 * need to be available internally. TODO Need to use template for the endpoint
	 * URL for the REST service
	 * 
	 * @param id
	 * @return
	 */
	public ProductDetails getProduct(Long id) {
		ProductDetails productDetails = new ProductDetails(id);
		try {
			ProductRestResponse productResponse = restTemplate.getForObject("https://redsky.target.com/v2/pdp/tcin/"
					+ id
					+ "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics",
					ProductRestResponse.class);

			if (productResponse.getProduct() != null && productResponse.getProduct().getItem() != null
					&& productResponse.getProduct().getItem().getProductDescription() != null
					&& productResponse.getProduct().getItem().getProductDescription().getTitle() != null)
				productDetails.setName(productResponse.getProduct().getItem().getProductDescription().getTitle());
		} catch (Exception e) {
			logger.warn("Exception Retreving the product infromation from from redsky.target.com for ID :" + id, e);
		}
		return productDetails;
	}

}
