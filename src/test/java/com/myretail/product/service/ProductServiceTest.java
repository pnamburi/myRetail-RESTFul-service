package com.myretail.product.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.myretail.product.domain.Item;
import com.myretail.product.domain.Product;
import com.myretail.product.domain.ProductDescription;
import com.myretail.product.domain.ProductDetails;
import com.myretail.product.domain.ProductRestResponse;

/**
 * Test class to test the ProductService which is the client for
 * redsky.target.com REST service
 * 
 * @author pnamb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {

	@Mock
	RestTemplate restTemplate;

	@MockBean
	ProductService productService;

	@Test
	public void getProductTest() {
		// Create a sample response object
		ProductRestResponse response = new ProductRestResponse();
		Product product = new Product();
		response.setProduct(product);
		Item item = new Item();
		product.setItem(item);
		ProductDescription pd = new ProductDescription();
		pd.setTitle("The Big Lebowski (Blu-ray) (Widescreen)");
		item.setProductDescription(pd);

		// Use Mock RestTemplate
		Mockito.when(productService.getTrustAllCertRestTemplate()).thenReturn(restTemplate);
		// Call the real method for the actual test method
		Mockito.when(productService.getProduct(13860428L)).thenCallRealMethod();
		// Mock the REST call
		Mockito.when(restTemplate.getForObject(
				"https://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics",
				ProductRestResponse.class)).thenReturn(response);
		// Call the method we are going to test
		ProductDetails productDetails = productService.getProduct(13860428L);

		assertNotNull(product);

		assertEquals("The Big Lebowski (Blu-ray) (Widescreen)", productDetails.getName());

		productDetails = productService.getProduct(1234L);
		assertNull(productDetails);
	}

}
