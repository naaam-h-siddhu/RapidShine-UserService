package com.rapidshine.carwash.user_service.dto;

import com.rapidshine.carwash.user_service.model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String address;
    private String phoneNumber;
}
