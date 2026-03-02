package com.appointment.booking.appointment_booking_system.Repository;

import com.appointment.booking.appointment_booking_system.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID>
{
    List<Slot>findByProviderId(UUID providerId);
}
