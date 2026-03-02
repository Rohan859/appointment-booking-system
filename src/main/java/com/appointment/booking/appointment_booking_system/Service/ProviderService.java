package com.appointment.booking.appointment_booking_system.Service;

import com.appointment.booking.appointment_booking_system.DTO.CreateProviderRequest;
import com.appointment.booking.appointment_booking_system.DTO.UpdateProviderRequest;
import com.appointment.booking.appointment_booking_system.Entity.Provider;
import com.appointment.booking.appointment_booking_system.Entity.User;
import com.appointment.booking.appointment_booking_system.Exception.ResourceNotFoundException;
import com.appointment.booking.appointment_booking_system.Repository.ProviderRepository;
import com.appointment.booking.appointment_booking_system.Repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProviderService
{
    private final UserRepository userRepository;
    private final ProviderRepository providerRepository;

    public ProviderService(UserRepository userRepository, ProviderRepository providerRepository) {
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
    }


    public Provider addProvider(CreateProviderRequest createProviderRequest)
    {
        //check first if the user exists
        User user = userRepository.findById(createProviderRequest.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + createProviderRequest.userId()));

        Provider provider = Provider.builder()
                .user(user)
                .specialization(createProviderRequest.specialization())
                .experience(createProviderRequest.experience())
                .build();

        providerRepository.save(provider);
        return provider;
    }

    public List<Provider> getAllProviders()
    {
        return providerRepository.findAll();
    }

    public Provider getProviderById(UUID id)
    {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found with id: " + id));
    }

    public String updateProvider(UUID id, UpdateProviderRequest updateProviderRequest)
    {
        //check if provider exists
        Provider provider = getProviderById(id);

        if(updateProviderRequest.specialization() != null && !updateProviderRequest.specialization().isEmpty())
        {
            provider.setSpecialization(updateProviderRequest.specialization());
        }

        if(updateProviderRequest.experience() > 0)
        {
            provider.setExperience(updateProviderRequest.experience());
        }

        providerRepository.save(provider);
        return "Provider with id: " + id + " updated successfully with specialization: " + provider.getSpecialization() + " and experience: " + provider.getExperience() + " years.";
    }

    public String deleteProvider(UUID id)
    {
        //check if provider exists
        Provider provider = getProviderById(id);
        providerRepository.delete(provider);
        return "Provider with id: " + id + " deleted successfully.";
    }
}
