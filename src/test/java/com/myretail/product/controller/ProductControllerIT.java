package com.myretail.product.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.myretail.product.domain.Price;
import com.myretail.product.domain.ProductDetails;
import com.myretail.product.model.ProductPricing;

/**
 * Integration test class to test the ProductController which is exposed as REST
 * service
 * 
 * @author pnamb
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {
	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	DynamoDBProxyServer server;

	ProductPricing sampleProductPricing = new ProductPricing(13860428L, 13.49, "USD");

	@BeforeClass
	public static void setpTest() {
		System.setProperty("sqlite4java.library.path", "native-libs");
		System.setProperty("aws.accessKeyId", "No_key");
		System.setProperty("aws.secretKey", "No_key");
	}

	/**
	 * Setup in-momory DynamoDB Database and create the table
	 * 
	 * @throws Exception
	 */
	@Before
	public void setup() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		// Start in memory DynamoDB Server
		final String[] localArgs = { "-inMemory" };
		server = ServerRunner.createServerFromCommandLineArgs(localArgs);
		server.start();
		try {
			// Create the table from ProductPricing model class
			CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(ProductPricing.class);
			tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

			amazonDynamoDB.createTable(tableRequest);
			// Save a product to DynamoDB before retrieve

		} catch (Exception e) {
			// Ignore exception, received if the table pre-created
		}
	}

	@Test
	public void getHello() throws Exception {
		dynamoDBMapper.save(sampleProductPricing);

		String productJson = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":13.49,\"currency_code\":\"USD\"}}";

		ResponseEntity<String> response = template.getForEntity(base.toString() + "/products/13860428", String.class);
		assertThat(response.getBody(), equalTo(productJson));

		dynamoDBMapper.delete(sampleProductPricing);
	}

	@Test
	public void UpdateProductTest() throws Exception {

		dynamoDBMapper.save(sampleProductPricing);
		
		ProductDetails sampleProductDetails = new ProductDetails(13860428L, "The Big Lebowski (Blu-ray)",
				new Price(17.49, "USD"));
		//Save the updated price
		template.put(base.toString() + "/products/13860428", sampleProductDetails);
		
		//Retrive updated product details with new price
		ResponseEntity<String> response = template.getForEntity(base.toString() + "/products/13860428", String.class);
		
		String expectedJson = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":17.49,\"currency_code\":\"USD\"}}";
		assertThat(response.getBody(), equalTo(expectedJson));

		dynamoDBMapper.delete(sampleProductPricing);
	}

	@After
	public void cleanup() throws Exception {
		server.stop();

	}
}
