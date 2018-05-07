package com.myretail.product.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myretail.product.application.Application;
import com.myretail.product.domain.ProductDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductServiceTest {
	@Autowired
	ProductService productService;

	@Test
	public void getProductTest() {
	
		ProductDetails product = productService.getProduct(13860428L);
		
		assertNotNull(product);
	}

	
}
