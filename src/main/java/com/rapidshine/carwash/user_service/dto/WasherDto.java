package com.rapidshine.carwash.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class WasherDto {

    private String name;
    private String email;
    private boolean isAvailable;
    public WasherDto(UserDto userDto){
        this.name= userDto.getName();
        this.email = userDto.getEmail();
        this.isAvailable = true;
    }
}
