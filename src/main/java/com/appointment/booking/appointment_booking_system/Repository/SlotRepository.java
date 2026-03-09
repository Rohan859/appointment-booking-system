package com.appointment.booking.appointment_booking_system.Repository;

import com.appointment.booking.appointment_booking_system.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SlotRepository extends JpaRepository<Slot, UUID>
{
    List<Slot>findByProviderId(UUID providerId);

    Slot findFirstByProviderId(UUID providerId);
}
