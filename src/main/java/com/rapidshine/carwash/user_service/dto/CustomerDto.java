package com.rapidshine.carwash.user_service.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    String name;
    @Email
    String email;
    public CustomerDto(UserDto userDto){
        this.name = userDto.getName();
        this.email = userDto.getEmail();

    }
}
