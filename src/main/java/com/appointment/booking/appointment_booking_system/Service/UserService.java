package com.appointment.booking.appointment_booking_system.Service;

import com.appointment.booking.appointment_booking_system.DTO.CreateUserRequest;
import com.appointment.booking.appointment_booking_system.Entity.User;
import com.appointment.booking.appointment_booking_system.Enum.UserStatus;
import com.appointment.booking.appointment_booking_system.Exception.ResourceNotFoundException;
import com.appointment.booking.appointment_booking_system.Repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest createUserRequest)
    {
        if(userRepository.existsByEmail(createUserRequest.getEmail()))
        {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .role(createUserRequest.getRole())
                .status(UserStatus.ACTIVE)
                .build();

        return userRepository.save(user);
    }

    public User getUserById(UUID id)
    {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }

    public String updateUserName(UUID id, String name)
    {
        User user = getUserById(id);
        user.setName(name);
        userRepository.save(user);
        return "User name - "+ name+ " updated successfully";
    }

    public String updateUserEmail(UUID id, String email)
    {
        User user = getUserById(id);

        if(userRepository.existsByEmail(email))
        {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setEmail(email);
        userRepository.save(user);
        return "User email - "+ email+ " updated successfully for user id: "+ id;
    }

    public String updateUserPassword(UUID id, String password)
    {
        if(password == null || password.isEmpty())
        {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        User user = getUserById(id);
        if(password.equals(user.getPassword()))
        {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }
        user.setPassword(password);
        userRepository.save(user);
        return "User password updated successfully for user id: "+ id;
    }

    public String updateUserStatus(UUID id, UserStatus userStatus)
    {
        User user = getUserById(id);
        user.setStatus(userStatus);
        userRepository.save(user);
        return "User status updated to "+ userStatus+ " for user id: "+ id;
    }
}
