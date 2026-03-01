package com.appointment.booking.appointment_booking_system.DTO;

import com.appointment.booking.appointment_booking_system.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUserRequest
{
    private String name;
    private String email;
    private String password;
    private Role role;
}
