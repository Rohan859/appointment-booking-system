package com.appointment.booking.appointment_booking_system.Controller;

import com.appointment.booking.appointment_booking_system.DTO.BookingRequest;
import com.appointment.booking.appointment_booking_system.Entity.Booking;
import com.appointment.booking.appointment_booking_system.Service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController
{
    private final BookingService bookingService;

    public BookingController(BookingService bookingService)
    {
        this.bookingService = bookingService;
    }

    @PostMapping("/")
    public ResponseEntity<Booking>createBooking(@RequestBody BookingRequest bookingRequest)
    {
        Booking booking = bookingService.createBooking(bookingRequest.userId(), bookingRequest.slotId());
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable UUID bookingId)
    {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable UUID userId)
    {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    @GetMapping("/slot/{slotId}")
    public ResponseEntity<List<Booking>> getBookingsBySlotId(@PathVariable UUID slotId)
    {
        return ResponseEntity.ok(bookingService.getBookingsBySlotId(slotId));
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<Booking>> getBookingsByProviderId(@PathVariable UUID providerId)
    {
        return ResponseEntity.ok(bookingService.getBookingsByProviderId(providerId));
    }

    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable UUID bookingId)
    {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }
}
