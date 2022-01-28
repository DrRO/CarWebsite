package com.udacity.pricing.priceApiTest;

import com.udacity.pricing.domain.price.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)

@AutoConfigureMockMvc
@AutoConfigureJsonTesters

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

/*Test of the Pricing Service Application. */

public class PriceTest {



    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

// Test getPrice() method
    @Test
    public void getPrice() {
// Test uri whith vehicleId
        String uri = "http://localhost:" + port + "/services/price?vehicleId=1";

        ResponseEntity<Price> response = this.testRestTemplate.getForEntity(uri, Price.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }




}