package com.rapidshine.carwash.user_service.controller;

import com.rapidshine.carwash.user_service.dto.LoginResponseDto;
import com.rapidshine.carwash.user_service.dto.UserDto;
import com.rapidshine.carwash.user_service.dto.UserProfileResponse;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.service.UserService;
import com.rapidshine.carwash.user_service.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Service APIS")

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
    public LoginResponseDto userSignup(@Valid @RequestBody UserDto userDTO) {
        User user = userService.saveUser(userDTO, "Email");
        String token = jwtUtil.generateToken(user.getEmail(), user.getUserRole().name());
        UserDto userDto = new UserDto(user.getName(), user.getEmail(), user.getPassword(), user.getUserRole(),user.getAddress(),user.getPhoneNumber());
        return new LoginResponseDto(token, userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody UserDto userDTO) {
        return userService.login(userDTO.getEmail(), userDTO.getPassword());
    }

    @GetMapping("/profile")
    public UserProfileResponse getProfile(Authentication authentication) {
        String email = authentication.getName();
        System.out.println(email);
        return userService.getUserProfile(email);
    }

    @PutMapping("/profile")
    public UserProfileResponse updateProfile(Authentication authentication, @RequestBody UserDto userDto) {
        String email = authentication.getName();
        return userService.updateUserProfile(email, userDto);
    }
}
