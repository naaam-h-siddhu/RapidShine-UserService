package com.rapidshine.carwash.user_service.service;

import com.rapidshine.carwash.user_service.dto.*;
import com.rapidshine.carwash.user_service.exceptions.UserAlreadyException;
import com.rapidshine.carwash.user_service.exceptions.UserNotFoundException;
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


    public User saveUser(UserDto userDTO, String auth) throws  RuntimeException{
        User exitingUser = userRepository.findByEmail(userDTO.getEmail()).orElse(null);

//        if(userDTO.getEmail().matches("[a-zA-Z0-9-._]+@[a-zA-Z0-9]+\\.[a-z0-9]{2,}")){
//
//        }
        if(exitingUser != null){
            throw new UserAlreadyException("User Already Exist with email: "+userDTO.getEmail());
        }
        User user = new User(userDTO.getName(),userDTO.getEmail(),userDTO.getPhoneNumber(),userDTO.getPassword(),userDTO.getUserRole(),
                userDTO.getAddress(),auth);
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
                .orElseThrow(() -> new RuntimeException("Invalid email")); // Generic error to prevent email enumeration

        if (!user.getAuth().equals("Email")) {
            throw new UserNotFoundException("Invalid Email ");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getUserRole().name());
        UserDto userDto = new UserDto(user.getName(), user.getEmail(), null, user.getUserRole(),user.getAddress(),user.getPhoneNumber());

        return ResponseEntity.ok(new LoginResponseDto(token, userDto));
    }

    public UserProfileResponse getUserProfile(String email){
        User user =  userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return new UserProfileResponse(user.getName(), user.getEmail(), user.getUserRole(),user.getAddress(),
                user.getPhoneNumber());
    }
    public UserProfileResponse updateUserProfile(String email, UserDto userDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDto.getName());

        // Optional password update
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty() && "Email".equals(user.getAuth())) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // ✅ Update address and phone number
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());

        User updatedUser = userRepository.save(user);

        return new UserProfileResponse(
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getUserRole(),
                updatedUser.getAddress(),
                updatedUser.getPhoneNumber()
        );
    }


    //helper method
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
}
