package com.udacity.vehicles.api;


import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.service.CarService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
class CarController {

    private final CarService carService;
    private final CarResourceAssembler assembler;

    CarController(CarService carService, CarResourceAssembler assembler) {
        this.carService = carService;
        this.assembler = assembler;
    }

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     */
    @GetMapping
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources = carService.list().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }



    @GetMapping("/{id}")
    Resource<Car> get(@PathVariable Long id) {

        //I Use the `findById` method from the Car Service to get car id
        Car carId = carService.findById(id);


        //I  Use the `assembler` on that car and return all information for car
        return assembler.toResource(carId);


    }


    //Posts information to create a new vehicle in the system.
    // throws URISyntaxException if the request contains errors
    @PostMapping
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {

        //save the input car,by using  the `save` method from the Car Service
        Car save = carService.save(car);

        // I Use the `assembler` on that saved car
        Resource<Car> resource = assembler.toResource(save);

        URI uri = new URI(resource.getId().expand().getHref());

       // return response that the new vehicle was added
        return ResponseEntity.created(uri).body(resource);


    }



    //Updates the information of a vehicle
    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) {


       //I set the id of the input car object to the `id` input.
        car.setId(id);

       // I using the `save` method from the Car service to save the car
        Car carUpdate = carService.save(car);

        // I use the `assembler` on that updated car
        Resource<Car> resource = assembler.toResource(carUpdate);

        //return response that the vehicle was updated
        return ResponseEntity.ok(resource);


    }


    // Delete a vehicle
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {


       // I use delete method on CarService to delete the requested vehicle
        carService.delete(id);

       //return response that the related vehicle is deleted
        return ResponseEntity.noContent().build();

    }
}
