package com.appointment.booking.appointment_booking_system.Controller;

import com.appointment.booking.appointment_booking_system.DTO.CreateSlotRequest;
import com.appointment.booking.appointment_booking_system.DTO.UpdateSlotStatusRequest;
import com.appointment.booking.appointment_booking_system.Entity.Slot;
import com.appointment.booking.appointment_booking_system.Service.SlotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SlotController
{
    private final SlotService slotService;

    public SlotController(SlotService slotService)
    {
        this.slotService = slotService;
    }


    @PostMapping("providers/{providerId}/slots")
    public ResponseEntity<Slot> createSlotsForProvider(@PathVariable UUID providerId,
                                                       @RequestBody CreateSlotRequest createSlotRequest)
    {
        // Logic to create slots for a provider
        Slot slot = slotService.createSlotsForProvider(providerId, createSlotRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(slot);
    }

    @GetMapping("providers/{providerId}/slots")
    public ResponseEntity<List<Slot>> getSlotsByProviderId(@PathVariable UUID providerId)
    {
        return ResponseEntity.ok(slotService.getSlotsByProviderId(providerId));
    }

    @GetMapping("/slots/{slotId}")
    public ResponseEntity<Slot> getSlotById(@PathVariable UUID slotId)
    {
        return ResponseEntity.ok(slotService.getSlotById(slotId));
    }

    @PatchMapping("/slots/{slotId}/status")
    public ResponseEntity<String> updateSlotStatus(@PathVariable UUID slotId,
                                                   @Valid @RequestBody UpdateSlotStatusRequest request)
    {
        return ResponseEntity.ok(slotService.updateSlotStatus(slotId, request));
    }

    @DeleteMapping("/slots/{slotId}")
    public ResponseEntity<String> deleteSlot(@PathVariable UUID slotId)
    {
        return ResponseEntity.ok(slotService.deleteSlot(slotId));
    }
}
