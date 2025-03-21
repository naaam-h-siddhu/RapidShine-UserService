package com.rapidshine.carwash.user_service.service;

import com.rapidshine.carwash.user_service.dto.CustomerDto;
import com.rapidshine.carwash.user_service.dto.UserDto;
import com.rapidshine.carwash.user_service.dto.WasherDto;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.model.UserRole;
import com.rapidshine.carwash.user_service.repository.UserRepository;
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

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);


    public User saveUser(UserDto userDTO,String auth){
        User user = new User(userDTO.getName(),userDTO.getEmail(),null,userDTO.getPassword(),userDTO.getUserRole(),
                null,auth);
        if(auth.equals("Email")){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
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
}
