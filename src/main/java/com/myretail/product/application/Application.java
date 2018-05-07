package com.myretail.product.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Spring Boot application
 * @author pnamb
 *
 */
@SpringBootApplication(scanBasePackages={"com.myretail.product"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
