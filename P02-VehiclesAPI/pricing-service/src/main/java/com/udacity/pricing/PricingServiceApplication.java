package com.udacity.pricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


//I  Convert the application from a REST API to a microservice.


@SpringBootApplication
// @EnableEurekaClient to tell the Spring to activate Eureka server related configration
@EnableEurekaClient


public class PricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

}
