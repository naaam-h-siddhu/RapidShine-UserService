package com.rapidshine.carwash.user_service.service;

import com.rapidshine.carwash.user_service.dto.CarDto;
import com.rapidshine.carwash.user_service.model.Car;
import com.rapidshine.carwash.user_service.model.Customer;
import com.rapidshine.carwash.user_service.repository.CarRepository;
import com.rapidshine.carwash.user_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Car saveCar(Long customerId,CarDto carDto){
        Car car = new Car(carDto.getBrand(),carDto.getModel(),carDto.getLicenceNumberPlate());
        Customer customer = customerRepository.getById(customerId);
        car.setCustomer(customer);
        carRepository.save(car);
        return car;

    }
}
