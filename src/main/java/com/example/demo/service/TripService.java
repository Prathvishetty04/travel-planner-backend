package com.example.demo.service;

import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepo;

    @Autowired
    private UserRepository userRepo;

    public List<Trip> getAllTripsByUser(Long userId) {
        return tripRepo.findByUserUserId(userId);
    }

    public Trip addTrip(Trip trip, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        trip.setUser(user);
        return tripRepo.save(trip);
    }

    public Optional<Trip> getTripById(Long tripId) {
        return tripRepo.findById(tripId);
    }

    public Trip updateTrip(Long tripId, Trip updatedTrip, Long userId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (!trip.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this trip");
        }

        trip.setTripName(updatedTrip.getTripName());
        trip.setDestination(updatedTrip.getDestination());
        trip.setStartDate(updatedTrip.getStartDate());
        trip.setEndDate(updatedTrip.getEndDate());

        return tripRepo.save(trip);
    }

    @Transactional
    public void deleteTrip(Long tripId, Long userId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (!trip.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this trip");
        }

        tripRepo.delete(trip);
    }
}
