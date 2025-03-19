package com.rapidshine.carwash.user_service.service;

import com.rapidshine.carwash.user_service.dto.CustomerDto;
import com.rapidshine.carwash.user_service.dto.UserDto;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.model.UserRole;
import com.rapidshine.carwash.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerService customerService;
    public User saveUser(UserDto userDTO){
        User user = new User(userDTO.getName(),userDTO.getEmail(),userDTO.getPhoneNumber(),userDTO.getPassword(),
                userDTO.getUserRole(),userDTO.getAddress());

        userRepository.save(user);
        if(user.getUserRole() == (UserRole.CUSTOMER)){
            CustomerDto customerDto  = new CustomerDto(userDTO);
            customerService.createCustomer(user,customerDto);
        }

        return user;
    }
}
