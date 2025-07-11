package com.rapidshine.carwash.user_service.service;

import com.rapidshine.carwash.user_service.dto.WasherDto;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.model.Washer;
import com.rapidshine.carwash.user_service.repository.WasherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WasherService {
    @Autowired
    private WasherRepository washerRepository;
    public void createWasher(User user,WasherDto washerDto){
        Washer washer = new Washer(washerDto.getName(),washerDto.getEmail(),user.getAddress(),user.getPhoneNumber(),washerDto.isAvailable());
        washer.setUser(user);
        washerRepository.save(washer);
    }
}
