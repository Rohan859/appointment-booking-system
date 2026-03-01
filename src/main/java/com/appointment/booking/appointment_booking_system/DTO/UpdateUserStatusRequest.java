package com.appointment.booking.appointment_booking_system.DTO;

import com.appointment.booking.appointment_booking_system.Enum.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record UpdateUserStatusRequest(UserStatus status)
{

}
