package com.myretail.product.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.myretail.product.model.ProductPricing;

public class SetupLocalDynamoDB {
	static {
		System.setProperty("sqlite4java.library.path", "native-libs");
		System.setProperty("aws.accessKeyId", "No_key");
		System.setProperty("aws.secretKey", "No_key");
	}
	public static void main(String[] args) throws Exception {
		AmazonDynamoDB dynamodb = null;
		// Create an shared DB of DynamoDB Local that runs over HTTP.
		// This allows the date created here to be used in subsequent Dynamo DB starts.
		final String[] localArgs = { "-sharedDb" };
		DynamoDBProxyServer server = null;
		
			server = ServerRunner.createServerFromCommandLineArgs(localArgs);
			server.start();

			dynamodb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(

					new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1")).build();
			DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamodb);
			try {
				// Create the table from ProductPricing model class
				CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(ProductPricing.class);
				tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
				dynamodb.createTable(tableRequest);
			} catch (Exception e) {
				// Ignore exception, received if the table pre-created
			}
			// Create an item/product in the dynamo DB table
			ProductPricing dave = new ProductPricing(13860428L, 13.44, "USD");
			dynamoDBMapper.save(dave);

		
	}

}
