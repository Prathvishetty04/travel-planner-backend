package com.example.demo.controller;

import com.example.demo.model.Trip;
import com.example.demo.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping("/user/{userId}")
    public List<Trip> getUserTrips(@PathVariable Long userId) {
        return tripService.getAllTripsByUser(userId);
    }

    @PostMapping("/user/{userId}")
    public Trip createTrip(@PathVariable Long userId, @RequestBody Trip trip) {
        return tripService.addTrip(trip, userId);
    }

    @PutMapping("/{tripId}/user/{userId}")
    public Trip updateTrip(@PathVariable Long tripId, @PathVariable Long userId, @RequestBody Trip updated) {
        return tripService.updateTrip(tripId, updated, userId);
    }

    @DeleteMapping("/{tripId}/user/{userId}")
    public void deleteTrip(@PathVariable Long tripId, @PathVariable Long userId) {
        tripService.deleteTrip(tripId, userId);
    }
}
