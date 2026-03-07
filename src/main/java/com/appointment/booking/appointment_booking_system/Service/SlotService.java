package com.appointment.booking.appointment_booking_system.Service;


import com.appointment.booking.appointment_booking_system.DTO.CreateSlotRequest;
import com.appointment.booking.appointment_booking_system.DTO.UpdateSlotStatusRequest;
import com.appointment.booking.appointment_booking_system.Entity.Provider;
import com.appointment.booking.appointment_booking_system.Entity.Slot;
import com.appointment.booking.appointment_booking_system.Enum.SlotStatus;
import com.appointment.booking.appointment_booking_system.Exception.ResourceNotFoundException;
import com.appointment.booking.appointment_booking_system.Repository.ProviderRepository;
import com.appointment.booking.appointment_booking_system.Repository.SlotRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SlotService
{
    private final SlotRepository slotRepository;
    private final ProviderRepository providerRepository;

    public SlotService(SlotRepository slotRepository, ProviderRepository providerRepository)
    {
        this.slotRepository = slotRepository;
        this.providerRepository = providerRepository;
    }

    @Transactional
    public Slot createSlotsForProvider(UUID providerId, CreateSlotRequest createSlotRequest)
    {
        //check if provider exists
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found with id: " + providerId));

        //check the start time is before end time
        if(createSlotRequest.startTime().isAfter(createSlotRequest.endTime()))
        {
            throw new IllegalArgumentException("Start time must be after end time");
        }

        //check capacity is positive
        if(createSlotRequest.capacity() <= 0)
        {
            throw new IllegalArgumentException("Capacity must be a positive number");
        }

        Slot slot = Slot.builder()
                .date(createSlotRequest.date())
                .startTime(createSlotRequest.startTime())
                .endTime(createSlotRequest.endTime())
                .capacity(createSlotRequest.capacity())
                .availableCapacity(createSlotRequest.capacity())
                .slotStatus(SlotStatus.OPEN)
                .provider(provider)
                .build();

        return slotRepository.save(slot);
    }

    public List<Slot> getSlotsByProviderId(UUID providerId)
    {
        //check if provider exists
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found with id: " + providerId));

        return slotRepository.findByProviderId(providerId);
    }

    public Slot getSlotById(UUID slotId)
    {
        return slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + slotId));
    }

    @Transactional
    public String updateSlotStatus(UUID slotId, @Valid UpdateSlotStatusRequest request)
    {
        Slot slot = getSlotById(slotId);
        slot.setSlotStatus(request.slotStatus());
        slotRepository.save(slot);
        return "Slot status updated successfully to " + request.slotStatus() + " for slot id: " + slotId;
    }

    @Transactional
    public String deleteSlot(UUID slotId)
    {
        Slot slot = getSlotById(slotId);
        slotRepository.delete(slot);
        return "Slot with id: " + slotId + " deleted successfully.";
    }
}
