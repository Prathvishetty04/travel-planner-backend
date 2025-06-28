package com.example.demo.service;

import com.example.demo.model.Itinerary;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.repository.ItineraryRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItineraryService {
    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    public Itinerary createItinerary(Itinerary itinerary) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Trip trip = itinerary.getTrip();
        if (trip == null || trip.getTripId() == null) {
            throw new IllegalArgumentException("Trip ID is required");
        }

        Optional<Trip> optionalTrip = tripRepository.findById(trip.getTripId());
        if (optionalTrip.isPresent() && optionalTrip.get().getUser().getUsername().equals(username)) {
            itinerary.setTrip(optionalTrip.get());
            return itineraryRepository.save(itinerary);
        } else {
            throw new SecurityException("Unauthorized to add itinerary to this trip");
        }
    }

    public List<Itinerary> getAllItineraries() {
        return itineraryRepository.findAll();
    }

    public Itinerary getItineraryById(Long id) {
        return itineraryRepository.findById(id).orElse(null);
    }

    public void deleteItinerary(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<Itinerary> optional = itineraryRepository.findById(id);
        if (optional.isPresent()) {
            Itinerary itinerary = optional.get();
            if (itinerary.getTrip().getUser().getUsername().equals(username)) {
                itineraryRepository.deleteById(id);
            } else {
                throw new SecurityException("You do not own this itinerary.");
            }
        }
    }

}