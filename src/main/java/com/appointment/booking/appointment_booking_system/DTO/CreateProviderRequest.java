package com.appointment.booking.appointment_booking_system.DTO;

import java.util.UUID;

public record CreateProviderRequest(
        UUID userId,
        String specialization,
        int experience)
{

}
