package com.udacity.vehicles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;


/*Annotations Configure Swagger using a Docket Bean*/

@Configuration
@EnableSwagger2



/*Adding a config package to Vehicles API and add a SwaggerConfig class within it*/

public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }


    //API info which appear on http://localhost:8080/swagger-ui.html#/   including Title, Description , Version, Url, contact info and license.
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "CAR REST API",
                "This API returns a list of CARS.",
                "1.0",
                "http://www.udacity.com/tos",
                new Contact("Rasha", "www.rasha.com", "rasha@udacity.com"),
                "License of API", "http://www.udacity.com/license", Collections.emptyList());
    }

}