package com.rapidshine.carwash.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    String name;
    String phoneNumber;
    String address;
    public CustomerDto(UserDto userDto){
        this.name = userDto.getName();
        this.address = userDto.getAddress();
        this.phoneNumber = userDto.getPhoneNumber();
    }
}
