package com.rapidshine.carwash.user_service.service;

import com.rapidshine.carwash.user_service.dto.*;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.model.UserRole;
import com.rapidshine.carwash.user_service.repository.UserRepository;
import com.rapidshine.carwash.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WasherService washerService;

    @Autowired
    private JwtUtil jwtUtil;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);


    public User saveUser(UserDto userDTO, String auth){
        User exitingUser = userRepository.findByEmail(userDTO.getEmail()).orElse(null);

        if(exitingUser != null){
            return exitingUser;
        }
        User user = new User(userDTO.getName(),userDTO.getEmail(),null,userDTO.getPassword(),userDTO.getUserRole(),
                null,auth);
        if(auth.equals("Email")){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }



        // Crete customer or wahswer based on role
        userRepository.save(user);
        if(user.getUserRole() == (UserRole.CUSTOMER)){
            CustomerDto customerDto  = new CustomerDto(userDTO);
            customerService.createCustomer(user,customerDto);
        }
        if(user.getUserRole() == UserRole.WASHER){

            WasherDto washerDto = new WasherDto(userDTO);
            washerService.createWasher(user,washerDto);

        }

        return user;
    }
    public ResponseEntity<LoginResponseDto> login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password")); // Generic error to prevent email enumeration

        if (!user.getAuth().equals("Email")) {
            throw new RuntimeException("Invalid login method");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getUserRole().name());
        UserDto userDto = new UserDto(user.getName(), user.getEmail(), null, user.getUserRole());

        return ResponseEntity.ok(new LoginResponseDto(token, userDto));
    }

    public UserProfileResponse getUserProfile(String email){
        User user =  userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return new UserProfileResponse(user.getName(), user.getEmail(), user.getUserRole(),user.getAddress(),
                user.getPhoneNumber());
    }
    public UserProfileResponse updateUserProfile(String email,UserDto userDto){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDto.getName());
        if(userDto.getPassword() != null && !userDto.getPassword().isEmpty() && "Email".equals(user.getAuth())){
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
//        user.setAddress(userDto);
        User temp =  userRepository.save(user);
        return new UserProfileResponse(temp.getName(), temp.getEmail(), temp.getUserRole(), user.getAddress(),
                user.getPhoneNumber());
    }

    //helper method
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
}
