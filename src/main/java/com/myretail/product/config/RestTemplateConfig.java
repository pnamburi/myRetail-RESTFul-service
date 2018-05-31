package com.myretail.product.config;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Configuration file to Configure the DynamoDB Database client and the
 * data mapper
 * 
 * @author pnamb
 *
 */
@Configuration
public class RestTemplateConfig {
	Logger logger = Logger.getLogger(RestTemplateConfig.class);
	/**
	 * We don't have any SSL certificates to accept, accept all the certificates for
	 * now.
	 * 
	 * @return
	 */
	@Bean
	protected RestTemplate getTrustAllCertRestTemplate() {
		
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		// TODO This is a borrowed code from Internet, may need to revise
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

			logger.warn("Exception While creating RestTemplate with trust all certificates ", e);
		}
		return new RestTemplate();
	}
}
