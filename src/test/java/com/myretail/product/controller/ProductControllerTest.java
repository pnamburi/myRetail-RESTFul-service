package com.myretail.product.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.myretail.product.dao.ProductPricingDAO;
import com.myretail.product.domain.Price;
import com.myretail.product.domain.ProductDetails;
import com.myretail.product.model.ProductPricing;
import com.myretail.product.service.ProductService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@WebMvcTest(value = ProductController.class)
public class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@MockBean
	private ProductPricingDAO productPricingDAO;

	ProductDetails productDetails = new ProductDetails(13860428, "\"The Big Lebowski (Blu-ray) (Widescreen)",
			new Price(13.49, "USD"));
	ProductPricing ProductPricing = new ProductPricing(13860428L, 13.49, "USD");

	String exampleProductJson = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray) (Widescreen)\",\"current_price\":{\"value\": 13.49,\"currency_code\":\"USD\"}}";

	@Test
	public void getProductTest() throws Exception {

		Mockito.when(productService.getProduct(Mockito.anyLong())).thenReturn(productDetails);
		Mockito.when(productPricingDAO.retrieveProduct(Mockito.anyLong())).thenReturn(ProductPricing);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/13860428")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$id", is(1))).andExpect(jsonPath("$[0].description", is("Lorem ipsum")))
				.andExpect(jsonPath("$[0].title", is("Foo")));
	}
	@Test
	public void UpdateProductTest() throws Exception {

		Course mockCourse = new Course("1", "Smallest Number", "1",
				Arrays.asList("1", "2", "3", "4"));

		// studentService.addCourse to respond back with mockCourse
		Mockito.doCallRealMethod().when(productPricingDAO.;

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/students/Student1/courses")
				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/students/Student1/courses/1",
				response.getHeader(HttpHeaders.LOCATION));
	}
}
