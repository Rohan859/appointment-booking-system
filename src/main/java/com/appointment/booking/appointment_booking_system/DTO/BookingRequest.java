package com.appointment.booking.appointment_booking_system.DTO;

import java.util.UUID;

public record BookingRequest(UUID slotId, UUID userId)
{

}
