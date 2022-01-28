package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private PriceClient priceClient;
    private MapsClient mapsClient;


    // Constuctor for CarService including CarRepository, MapsClient and Pricing Web Clients

    public CarService(CarRepository repository, PriceClient priceClient, MapsClient mapsClient) {


        this.repository = repository;
        this.priceClient = priceClient;
        this.mapsClient = mapsClient;
    }



     //get the list of all vehicles in the CarRepository

    public List<Car> list() {

        return repository.findAll();
    }



    // Gets car information by ID

    public Car findById(Long id) {

        Car car = new Car();

        //Find the car by ID from the `repository`
        Optional<Car> carid = repository.findById(id);

        //If it does not exist, throw a CarNotFoundException
        if (carid == null) {

            throw new CarNotFoundException();

        } else {
            car = carid.get();

        }

        // call the pricing service each time to get the price because The car class file uses @transient
        // Set the price of the car based on the `id`

        String price = priceClient.getPrice(id);
        car.setPrice(price);


        // call  the Maps service each time to get the location because The car class file uses @transient
        // Set the location of the car based on the `id`

        Location location = mapsClient.getAddress(car.getLocation());
        car.setLocation(location);

         //Return the Car object information, including location and price
        return car;
    }


     // Save either created or updated  vehicle as Udacity Starter source


    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                  //Set the updated value of condition attribute
                        carToBeUpdated.setCondition(car.getCondition());

                  //Set the updated value of details attribute
                        carToBeUpdated.setDetails(car.getDetails());

                   //Set the updated value of location attribute
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }



    //Deletes a given car by ID
    public void delete(Long id) {

        Car car = new Car();

        //Find the car by ID from the `repository`
        Optional<Car> carid = repository.findById(id);


        //If it does not exist, throw a CarNotFoundException
        if ( carid == null) {
            throw new CarNotFoundException();
        } else {
            car = (Car) carid.get();

  //Delete this car from the repository.
            repository.delete(car);
        }


    }
}
