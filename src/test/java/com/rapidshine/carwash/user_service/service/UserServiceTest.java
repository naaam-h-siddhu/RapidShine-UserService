package com.rapidshine.carwash.user_service.service;

import com.rapidshine.carwash.user_service.dto.*;
import com.rapidshine.carwash.user_service.exceptions.UserAlreadyException;
import com.rapidshine.carwash.user_service.exceptions.UserNotFoundException;
import com.rapidshine.carwash.user_service.model.User;
import com.rapidshine.carwash.user_service.model.UserRole;
import com.rapidshine.carwash.user_service.repository.UserRepository;
import com.rapidshine.carwash.user_service.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock private UserRepository userRepository;
    @Mock private CustomerService customerService;
    @Mock private WasherService washerService;
    @Mock private JwtUtil jwtUtil;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto("John", "john@example.com", "password", UserRole.CUSTOMER, "123 Street", "1234567890");
        user = new User("John", "john@example.com", "1234567890", "encoded-password", UserRole.CUSTOMER, "123 Street", "Email");
        user.setId(1L);
    }

    @Test
    void testSaveUserSuccess_EmailAuth_Customer() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(userDto, "Email");

        assertNotNull(savedUser);
        assertEquals(userDto.getEmail(), savedUser.getEmail());
        verify(customerService).createCustomer(any(User.class), any(CustomerDto.class));
    }

    @Test
    void testSaveUser_WasherRole() {
        userDto.setUserRole(UserRole.WASHER);
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(userDto, "Email");

        assertNotNull(savedUser);
        verify(washerService).createWasher(any(User.class), any(WasherDto.class));
    }

    @Test
    void testSaveUserAlreadyExists_ThrowsException() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyException.class, () -> userService.saveUser(userDto, "Email"));
    }



    @Test
    void testLoginInvalidPassword() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        user.setAuth("Email");
        user.setPassword(new BCryptPasswordEncoder().encode("real-password"));

        assertThrows(RuntimeException.class, () -> userService.login(user.getEmail(), "wrong-password"));
    }

    @Test
    void testLoginNonEmailAuth_Throws() {
        user.setAuth("Google");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserNotFoundException.class, () -> userService.login(user.getEmail(), "password"));
    }

    @Test
    void testGetUserProfileSuccess() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserProfileResponse profile = userService.getUserProfile(user.getEmail());

        assertEquals(user.getEmail(), profile.getEmail());
    }

    @Test
    void testUpdateUserProfile() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto updateDto = new UserDto("Updated", user.getEmail(), "newpass", user.getUserRole(), "New Addr", "9999999999");

        UserProfileResponse updated = userService.updateUserProfile(user.getEmail(), updateDto);

        assertEquals("Updated", updated.getName());
        assertEquals("New Addr", updated.getAddress());
        assertEquals("9999999999", updated.getPhoneNumber());
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        User found = userService.findByEmail(user.getEmail());

        assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    void testFindByEmail_NotFound() {
        when(userRepository.findByEmail("nope@example.com")).thenReturn(Optional.empty());
        assertNull(userService.findByEmail("nope@example.com"));
    }
}
