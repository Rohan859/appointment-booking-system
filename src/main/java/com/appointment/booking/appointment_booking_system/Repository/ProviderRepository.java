package com.appointment.booking.appointment_booking_system.Repository;

import com.appointment.booking.appointment_booking_system.Entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID>
{

}
