package com.myretail.product.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.myretail.product.dao.ProductPricingDAO;
import com.myretail.product.domain.Price;
import com.myretail.product.domain.ProductDetails;
import com.myretail.product.model.ProductPricing;
import com.myretail.product.service.ProductService;
/**
 * Test class to test the ProductController which is exposed as REST service
 * @author pnamb
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@MockBean
	private ProductPricingDAO productPricingDAO;

	@Test
	public void getProductTest() throws Exception {
		ProductDetails productDetails = new ProductDetails(13860428, "The Big Lebowski (Blu-ray) (Widescreen)",
				new Price(13.49, "USD"));
		ProductPricing ProductPricing = new ProductPricing(13860428L, 13.49, "USD");

		Mockito.when(productService.getProduct(13860428L)).thenReturn(productDetails);
		Mockito.when(productPricingDAO.retrieveProduct(13860428)).thenReturn(ProductPricing);

		mockMvc.perform(get("/products/13860428")).andExpect(status().isOk()).andExpect(jsonPath("$.id", equalTo(13860428)))
				.andExpect(jsonPath("$.name", equalTo("The Big Lebowski (Blu-ray) (Widescreen)")))
				.andExpect(jsonPath("$.current_price.value", equalTo(13.49)))
				.andExpect(jsonPath("$.current_price.currency_code", equalTo("USD")));

	}

	@Test
	public void UpdateProductTest() throws Exception {
		String productJson = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray) (Widescreen)\",\"current_price\":{\"value\": 13.49,\"currency_code\":\"USD\"}}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/13860428").accept(MediaType.APPLICATION_JSON)
				.content(productJson).contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
}
