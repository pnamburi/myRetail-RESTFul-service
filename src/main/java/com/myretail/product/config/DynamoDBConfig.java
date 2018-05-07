package com.myretail.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration

public class DynamoDBConfig {

	@Bean
	@Profile({"prod","default"})
	public DynamoDBMapper dynamoDBMapper() {
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

		return dynamoDBMapper;
	}

	@Bean
	@Profile("local")
	public AmazonDynamoDB localDynamoDB() {
		System.setProperty("aws.accessKeyId", "No_key");
		System.setProperty("aws.secretKey", "No_key");
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"))
				.build(); 
		return amazonDynamoDB;
	}

	@Bean
	@Profile("local")
	public DynamoDBMapper localDynamoDBMapper() {

		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(localDynamoDB());

		return dynamoDBMapper;
	}

}