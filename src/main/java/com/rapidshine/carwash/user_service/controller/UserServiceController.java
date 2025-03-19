package com.rapidshine.carwash.user_service.controller;

import com.rapidshine.carwash.user_service.dto.CarDto;
import com.rapidshine.carwash.user_service.dto.UserDto;
import com.rapidshine.carwash.user_service.model.Car;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.service.CarService;
import com.rapidshine.carwash.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserServiceController {
    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @PostMapping("/signup")
    public User userSignup(@RequestBody UserDto userDTO){
        return userService.saveUser(userDTO);
    }

    @PostMapping("/{customerid}/addcar")
    public Car addCarToCustomer(@PathVariable Long customerid, @RequestBody CarDto carDto){
        return carService.saveCar(customerid,carDto);
    }
}
