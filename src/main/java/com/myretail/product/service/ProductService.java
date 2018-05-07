package com.myretail.product.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jboss.logging.Logger;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myretail.product.domain.ProductDetails;
import com.myretail.product.domain.ProductRestResponse;

@Service
public class ProductService {
	Logger logger = Logger.getLogger(ProductService.class);
	RestTemplate restTemplate;

	public ProductDetails getProduct(Long id) {
		ProductDetails productDetails = new ProductDetails(id);
		try {
			ProductRestResponse productResponse = getTrustAllCertRestTemplate().getForObject(
					"https://redsky.target.com/v2/pdp/tcin/" + id
							+ "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics",
					ProductRestResponse.class);

			if (productResponse.getProduct() != null && productResponse.getProduct().getItem() != null
					&& productResponse.getProduct().getItem().getProductDescription() != null
					&& productResponse.getProduct().getItem().getProductDescription().getTitle() != null)
				productDetails.setName(productResponse.getProduct().getItem().getProductDescription().getTitle());
		} catch (Exception e) {
			logger.warn("Exception Retreving the product infromation from from redsky.target.com for ID :"+id,e);
		}
		return productDetails;
	}

	private RestTemplate getTrustAllCertRestTemplate() {
		//Check if rest template already created and return the existing
		if (restTemplate != null)
			return restTemplate;

		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext;
		try {
			sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
					.build();

			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

			requestFactory.setHttpClient(httpClient);
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			return restTemplate;
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {

			logger.warn("Exception While creating RestTemplate with trust all certificates ",e);
		}
		return new RestTemplate();
	}
}