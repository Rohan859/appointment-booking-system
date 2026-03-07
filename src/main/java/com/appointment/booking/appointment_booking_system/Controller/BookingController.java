package com.appointment.booking.appointment_booking_system.Controller;

import com.appointment.booking.appointment_booking_system.DTO.BookingRequest;
import com.appointment.booking.appointment_booking_system.Entity.Booking;
import com.appointment.booking.appointment_booking_system.Service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
