package com.appointment.booking.appointment_booking_system.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateSlotRequest(
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        int capacity
)
{

}
