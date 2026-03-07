package com.appointment.booking.appointment_booking_system.DTO;

import com.appointment.booking.appointment_booking_system.Enum.SlotStatus;

import java.io.Serializable;

public record UpdateSlotStatusRequest(SlotStatus slotStatus)
{

}
