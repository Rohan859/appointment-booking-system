package com.appointment.booking.appointment_booking_system.Service;

import com.appointment.booking.appointment_booking_system.Entity.Booking;
import com.appointment.booking.appointment_booking_system.Entity.Slot;
import com.appointment.booking.appointment_booking_system.Entity.User;
import com.appointment.booking.appointment_booking_system.Enum.BookingStatus;
import com.appointment.booking.appointment_booking_system.Enum.SlotStatus;
import com.appointment.booking.appointment_booking_system.Exception.ResourceNotFoundException;
import com.appointment.booking.appointment_booking_system.Repository.BookingRepository;
import com.appointment.booking.appointment_booking_system.Repository.SlotRepository;
import com.appointment.booking.appointment_booking_system.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BookingService
{
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SlotRepository slotRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, SlotRepository slotRepository)
    {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.slotRepository = slotRepository;
    }


    @Transactional
    public Booking createBooking(UUID userId, UUID slotId)
    {
        //check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        //check if slot exists and has available capacity
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + slotId));

        if(slot.getSlotStatus() != SlotStatus.OPEN)
        {
            throw new RuntimeException("Slot not available");
        }

        if(slot.getAvailableCapacity() <= 0)
        {
            throw new RuntimeException("Slot is fully booked");
        }

        slot.setAvailableCapacity(slot.getAvailableCapacity() - 1);

        if(slot.getAvailableCapacity() == 0)
        {
            slot.setSlotStatus(SlotStatus.CLOSED);
        }

        slotRepository.save(slot);

        Booking booking = Booking.builder()
                .user(user)
                .slot(slot)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();

        return bookingRepository.save(booking);
    }
}
