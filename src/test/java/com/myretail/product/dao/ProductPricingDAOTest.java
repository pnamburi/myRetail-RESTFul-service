package com.myretail.product.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.myretail.product.Application;
import com.myretail.product.model.ProductPricing;

/**
 * Test class to test the ProductPricingDAO which is the repository
 * interface for the DynamDB database access
 * 
 * @author pnamb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("local")
public class ProductPricingDAOTest {
	@Autowired
	ProductPricingDAO productPricingDAO;

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	DynamoDBProxyServer server;

	private static final Long EXPECTED_PRODUCT_ID = 1111111L;
	private static final double EXPECTED_PRICE = 50.55;
	private static final String EXPECTED_CURRENCY_CODE = "USD";

	@BeforeClass
	public static void setpTest() {
		System.setProperty("sqlite4java.library.path", "native-libs");
		System.setProperty("aws.accessKeyId", "No_key");
		System.setProperty("aws.secretKey", "No_key");
	}
/**
 * Setup in-momory DynamoDB Database and create the table
 * @throws Exception
 */
	@Before
	public void setup() throws Exception {
		// Start in memory DynamoDB Server
		final String[] localArgs = { "-inMemory" };
		server = ServerRunner.createServerFromCommandLineArgs(localArgs);
		server.start();
		try {
			// Create the table from ProductPricing model class
			CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(ProductPricing.class);
			tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

			amazonDynamoDB.createTable(tableRequest);
		} catch (Exception e) {
			// Ignore exception, received if the table pre-created
		}
	}

	@Test
	public void retrieveProductTest() {
		// Save a product to DynamoDB before retrieve
		ProductPricing dave = new ProductPricing(EXPECTED_PRODUCT_ID, EXPECTED_PRICE, EXPECTED_CURRENCY_CODE);
		dynamoDBMapper.save(dave);

		// Retrieve the saved product from DynamoDB
		ProductPricing productPricing = productPricingDAO.retrieveProduct(EXPECTED_PRODUCT_ID);
		assertNotNull(productPricing);
		assertTrue("Contains item with expected cost", productPricing.getPrice() == EXPECTED_PRICE);
		assertTrue("Contains item with expected currency code",
				productPricing.getCurrencyCode().equals(EXPECTED_CURRENCY_CODE));

		// Delete it after the test
		dynamoDBMapper.delete(dave);

		// Test for non existing product id
		ProductPricing productPricing2 = productPricingDAO.retrieveProduct(45454L);
		assertNull(productPricing2);
	}

	@Test
	public void updateProductTestCase() {
		// Save a product to DynamoDB before retrieve
		ProductPricing sampleProductPricing = new ProductPricing(EXPECTED_PRODUCT_ID, EXPECTED_PRICE, EXPECTED_CURRENCY_CODE);
		dynamoDBMapper.save(sampleProductPricing);

		// Retrieve the saved product from DynamoDB
		ProductPricing productPricing = productPricingDAO.retrieveProduct(EXPECTED_PRODUCT_ID);
		assertNotNull(productPricing);
		assertTrue("Contains item with expected cost", productPricing.getPrice() == EXPECTED_PRICE);
		assertTrue("Contains item with expected currency code",
				productPricing.getCurrencyCode().equals(EXPECTED_CURRENCY_CODE));

		// Set new price for the product
		sampleProductPricing.setPrice(60.66);
		dynamoDBMapper.save(sampleProductPricing);

		// Retrieve updated value
		productPricing = productPricingDAO.retrieveProduct(EXPECTED_PRODUCT_ID);
		assertNotNull(productPricing);
		assertTrue("Contains item with updated cost", productPricing.getPrice() == 60.66);

		// Delete it after the test
		dynamoDBMapper.delete(sampleProductPricing);

	}

	@After
	public void cleanup() throws Exception {
		server.stop();
	}

}
