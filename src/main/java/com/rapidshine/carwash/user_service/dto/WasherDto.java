package com.rapidshine.carwash.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    private boolean isAvailable;
    public WasherDto(UserDto userDto){
        this.name= userDto.getName();
        this.email = userDto.getEmail();
        this.isAvailable = true;
    }
}
