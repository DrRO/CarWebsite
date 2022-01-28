package com.udacity.vehicles.api;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.service.CarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.valueOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters

/*Test of the CarController class.*/

public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Car> json;

    @MockBean
    private CarService carService;

    @MockBean
    private PriceClient priceClient;

    @MockBean
    private MapsClient mapsClient;



    // Setup an example of car before  test
    @Before
    public void setup() {
        Car car = getCar();
        car.setId(1L);
        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.list()).willReturn(Collections.singletonList(car));
    }

// Test CRUD mehods
    // Implement of test to post car method using throws Exception during fail
    @Test
    public void createCar() throws Exception {

        // post method as Udacity Starter code
        Car car = getCar();
        mvc.perform(
                post(new URI("/cars"))
                        .content(json.write(car).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }


    // Implement of test to Get car method using throws Exception during fail
    @Test
    public void listCars() throws Exception {

        // get the list of vehicles using getCar() method as an an example Car object.

        URI uri = new URI("/cars");
        String content = json.write(getCar()).getJson();
        MediaType contentType = valueOf("application/json;charset=UTF-8");
//Test get method by passing uri , content , contentType
        mvc.perform(get(uri).content(content)
                        .contentType(contentType)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

    }


    // Implement of test read method using vehicle id by Getting car method using throws Exception during fail

    @Test
    public void findCar() throws Exception {

        URI uri = new URI("/cars/");
        String id = "1";
        MediaType contentType = valueOf("application/json;charset=UTF-8");


//Test get method by passing vehicle id  "/cars/1" , contentType

        mvc.perform(get(uri + id)
                        .contentType(contentType)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }



    // Implement of test update (put) method using vehicle id by Getting car method using throws Exception during fail

    @Test
    public void updateCar() throws Exception {


        URI uri = new URI("/cars/");
        String id = "1";
        String content = json.write(getCar()).getJson();

        MediaType contentType = valueOf("application/json;charset=UTF-8");
//Test get method by passing vehicle id  "/cars/1" , contentType

        mvc.perform(put(uri + id)
                .content(content)
                .contentType(contentType)


                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                //check if a vehicle is  updated
                .andExpect(status().isOk());
    }



    // Implement of test delete method using vehicle id by Getting car method using throws Exception during fail

    @Test
    public void deleteCar() throws Exception {


        URI uri = new URI("/cars/");
        String id = "1";
        String content = json.write(getCar()).getJson();

        MediaType contentType = valueOf("application/json;charset=UTF-8");
//Test get method by passing vehicle id  "/cars/1" , contentType

        mvc.perform(delete(uri + id)
                .content(content)
                .contentType(contentType)


                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                //check if a vehicle is  deleted
                .andExpect(status().isNoContent());
    }

    // Car object examle which used in tests
    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}