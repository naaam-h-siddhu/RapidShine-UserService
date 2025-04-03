package com.rapidshine.carwash.user_service.controller;

import com.rapidshine.carwash.user_service.dto.CarDto;
import com.rapidshine.carwash.user_service.dto.LoginResponseDto;
import com.rapidshine.carwash.user_service.dto.UserDto;
import com.rapidshine.carwash.user_service.model.Car;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.model.UserRole;
import com.rapidshine.carwash.user_service.service.UserService;
import com.rapidshine.carwash.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserServiceController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health")
    public String health() {
        return "HELLO SIDDHU";
    }

    @PostMapping("/signup")
    public LoginResponseDto userSignup(@RequestBody UserDto userDTO) {
        User user = userService.saveUser(userDTO, "Email");
        String token = jwtUtil.generateToken(user.getEmail(), user.getUserRole().name());
        UserDto userDto = new UserDto(user.getName(), user.getEmail(), null, user.getUserRole());
        return new LoginResponseDto(token, userDto);
    }

    @PostMapping("/login")
    public LoginResponseDto userLogin(@RequestBody UserDto userDTO) {
        return userService.login(userDTO.getEmail(), userDTO.getPassword());
    }

    @GetMapping("/profile")
    public User getProfile(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserProfile(email);
    }

    @PutMapping("/profile")
    public User updateProfile(Authentication authentication, @RequestBody UserDto userDto) {
        String email = authentication.getName();
        return userService.updateUserProfile(email, userDto);
    }


}