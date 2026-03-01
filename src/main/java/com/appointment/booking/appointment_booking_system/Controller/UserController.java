package com.appointment.booking.appointment_booking_system.Controller;

import com.appointment.booking.appointment_booking_system.DTO.CreateUserRequest;
import com.appointment.booking.appointment_booking_system.DTO.PasswordChangeRequest;
import com.appointment.booking.appointment_booking_system.DTO.UpdateUserStatusRequest;
import com.appointment.booking.appointment_booking_system.Entity.User;
import com.appointment.booking.appointment_booking_system.Enum.UserStatus;
import com.appointment.booking.appointment_booking_system.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/users")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<User>createUser(@RequestBody CreateUserRequest createUserRequest)
    {
            User newUser = userService.createUser(createUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id)
    {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String>updateUserName(@PathVariable UUID id, @RequestParam String name)
    {
        return ResponseEntity.ok(userService.updateUserName(id,name));
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<String>updateUserEmail(@PathVariable UUID id, @RequestParam String email)
    {
        return ResponseEntity.ok(userService.updateUserEmail(id,email));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String>updateUserPassword(@PathVariable UUID id, @RequestBody PasswordChangeRequest passwordChangeRequest)
    {
        return ResponseEntity.ok(userService.updateUserPassword(id,passwordChangeRequest.getNewpassword()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String>updateUserStatus(@PathVariable UUID id, @RequestBody UpdateUserStatusRequest updateUserStatusRequest)
    {
        return ResponseEntity.ok(userService.updateUserStatus(id,updateUserStatusRequest.status()));
    }
}
