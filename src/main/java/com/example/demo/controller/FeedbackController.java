package com.example.demo.controller;

import com.example.demo.model.Feedback;
import com.example.demo.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/{tripId}")
    public Feedback addFeedback(@PathVariable Long tripId,
                                @RequestBody Feedback feedback,
                                Principal principal) {
        String username = principal.getName();
        return feedbackService.addFeedback(username, tripId, feedback);
    }

    @GetMapping("/trip/{tripId}")
    public List<Feedback> getFeedbackForTrip(@PathVariable Long tripId) {
        return feedbackService.getFeedbackByTrip(tripId);
    }

}