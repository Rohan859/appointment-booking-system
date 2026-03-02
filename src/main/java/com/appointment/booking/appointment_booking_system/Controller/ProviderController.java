package com.appointment.booking.appointment_booking_system.Controller;

import com.appointment.booking.appointment_booking_system.DTO.CreateProviderRequest;
import com.appointment.booking.appointment_booking_system.DTO.UpdateProviderRequest;
import com.appointment.booking.appointment_booking_system.Entity.Provider;
import com.appointment.booking.appointment_booking_system.Service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/providers")
public class ProviderController
{
    private final ProviderService providerService;

    public ProviderController(ProviderService providerService)
    {
        this.providerService = providerService;
    }

    @PostMapping("/")
    public ResponseEntity<Provider> addProvider(@RequestBody CreateProviderRequest createProviderRequest)
    {
        Provider provider = providerService.addProvider(createProviderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(provider);
    }

    @GetMapping("/")
    public ResponseEntity<List<Provider>> getAllProviders()
    {
        return ResponseEntity.ok(providerService.getAllProviders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provider> getProviderById(@PathVariable UUID id)
    {
        return ResponseEntity.ok(providerService.getProviderById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProvider(@PathVariable UUID id, @RequestBody UpdateProviderRequest updateProviderRequest)
    {
        return ResponseEntity.ok(providerService.updateProvider(id, updateProviderRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProvider(@PathVariable UUID id)
    {
        return ResponseEntity.ok(providerService.deleteProvider(id));
    }
}
