package com.rapidshine.carwash.user_service.dto;

import com.rapidshine.carwash.user_service.model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*#$%^!@=+]).{8,20}$", message = "Password must be 8-20 " +
            "Characters long contains at least one digit, one lowercase, one uppercase , one special character, and no whitespace")
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String address;
    @Pattern(regexp = "[0-9]{10}",message = "phone number should have 10 numbers")
    private String phoneNumber;



    // Todo -> Add the regex verifications for all the fields


}
