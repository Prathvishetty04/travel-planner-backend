package com.example.demo.service;

import com.example.demo.model.Feedback;
import com.example.demo.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Feedback;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TripRepository tripRepo;

    public Feedback addFeedback(String username, Long tripId, Feedback feedback) {
        User user = userRepo.findByUsername(username).orElseThrow();
        Trip trip = tripRepo.findById(tripId).orElseThrow();

        feedback.setUser(user);
        feedback.setTrip(trip);
        return feedbackRepo.save(feedback);
    }

    public List<Feedback> getFeedbackByTrip(Long tripId) {
        return feedbackRepo.findByTripTripId(tripId);
    }
}
