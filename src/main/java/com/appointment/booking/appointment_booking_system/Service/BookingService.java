package com.appointment.booking.appointment_booking_system.Service;

import com.appointment.booking.appointment_booking_system.Entity.Booking;
import com.appointment.booking.appointment_booking_system.Entity.Provider;
import com.appointment.booking.appointment_booking_system.Entity.Slot;
import com.appointment.booking.appointment_booking_system.Entity.User;
import com.appointment.booking.appointment_booking_system.Enum.BookingStatus;
import com.appointment.booking.appointment_booking_system.Enum.SlotStatus;
import com.appointment.booking.appointment_booking_system.Exception.ResourceNotFoundException;
import com.appointment.booking.appointment_booking_system.Repository.BookingRepository;
import com.appointment.booking.appointment_booking_system.Repository.SlotRepository;
import com.appointment.booking.appointment_booking_system.Repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Booking getBookingById(UUID bookingId)
    {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }

    public List<Booking> getBookingsByUserId(UUID userId)
    {
        if (!userRepository.existsById(userId))
        {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return bookingRepository.getBookingsByUserId(userId);
    }

    public List<Booking> getBookingsBySlotId(UUID slotId)
    {
        if (!slotRepository.existsById(slotId))
        {
            throw new ResourceNotFoundException("Slot not found with id: " + slotId);
        }

        return bookingRepository.getBookingsBySlotId(slotId);
    }

    public List<Booking> getBookingsByProviderId(UUID providerId)
    {
        List<Slot> slots = slotRepository.findByProviderId(providerId);
        if (slots == null || slots.isEmpty()) {
            throw new ResourceNotFoundException("Provider not found with id: " + providerId);
        }

        return slots.stream()
                .flatMap(slot -> bookingRepository.getBookingsBySlotId(slot.getId()).stream())
                .collect(Collectors.toList());
    }

    @Transactional
    public String cancelBooking(UUID bookingId)
    {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (booking.getBookingStatus() == BookingStatus.CANCELLED)
        {
            throw new RuntimeException("Booking is already cancelled");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        Slot slot = booking.getSlot();
        slot.setAvailableCapacity(slot.getAvailableCapacity() + 1);
        if (slot.getSlotStatus() == SlotStatus.CLOSED)
        {
            slot.setSlotStatus(SlotStatus.OPEN);
        }
        slotRepository.save(slot);

        return "Booking cancelled successfully";
    }
}
