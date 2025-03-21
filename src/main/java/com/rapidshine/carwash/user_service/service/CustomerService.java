package com.rapidshine.carwash.user_service.service;

import com.rapidshine.carwash.user_service.dto.CustomerDto;
import com.rapidshine.carwash.user_service.model.Customer;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public void createCustomer(User user, CustomerDto customerDto){
        Customer customer = new Customer(customerDto.getName(),customerDto.getEmail(),
                null,null);
        customer.setUser(user);
        customerRepository.save(customer);
    }
}
