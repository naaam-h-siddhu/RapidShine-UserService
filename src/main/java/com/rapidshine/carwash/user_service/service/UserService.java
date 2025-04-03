package com.rapidshine.carwash.user_service.service;

import com.rapidshine.carwash.user_service.dto.CustomerDto;
import com.rapidshine.carwash.user_service.dto.LoginResponseDto;
import com.rapidshine.carwash.user_service.dto.UserDto;
import com.rapidshine.carwash.user_service.dto.WasherDto;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.model.UserRole;
import com.rapidshine.carwash.user_service.repository.UserRepository;
import com.rapidshine.carwash.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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


    public User saveUser(UserDto userDTO,String auth){
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
    public LoginResponseDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (user.getAuth().equals("Email") && passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateToken(user.getEmail(), user.getUserRole().name());
            UserDto userDto = new UserDto(user.getName(), user.getEmail(), null, user.getUserRole());
            return new LoginResponseDto(token, userDto);
        }
        throw new RuntimeException("Invalid credentials");
    }
    public User getUserProfile(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }
    public User updateUserProfile(String email,UserDto userDto){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDto.getName());
        if(userDto.getPassword() != null && !userDto.getPassword().isEmpty() && "Email".equals(user.getAuth())){
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        return userRepository.save(user);
    }

    //helper method
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
}
