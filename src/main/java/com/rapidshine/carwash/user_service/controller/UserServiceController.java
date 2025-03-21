package com.rapidshine.carwash.user_service.controller;

import com.rapidshine.carwash.user_service.dto.CarDto;
import com.rapidshine.carwash.user_service.dto.UserDto;
import com.rapidshine.carwash.user_service.model.Car;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserServiceController {
    @GetMapping("/heath")
    public String health(){
        return "HELLO SIDDHU";
    }
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User userSignup(@RequestBody UserDto userDTO){

        return userService.saveUser(userDTO,"Email");
    }
    // oauth/gmail -> password = null
    // oauth/facebook -> password = null



}
