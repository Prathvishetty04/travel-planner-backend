package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.*;
import com.example.demo.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(
			UserRepository userRepo,
			TripRepository tripRepo,
			HotelRepository hotelRepo,
			ExpenseRepository expenseRepo,
			ItineraryRepository itineraryRepo,
			FeedbackRepository feedbackRepo
	) {
		return args -> {
			// Create User
			User user = new User();
			user.setName("Prathvi");
			user.setEmail("prathvi@email.com");
			user.setUsername("prathvi123");
			user.setPassword("pass123");
			user.setAuthMethod("email");
			user = userRepo.save(user);

			// Create Trip
			Trip trip = new Trip();
			trip.setTripName("Goa Vacation");
			trip.setDestination("Goa");
			trip.setStartDate("2025-12-01");
			trip.setEndDate("2025-12-07");
			trip.setUser(user);
			trip = tripRepo.save(trip);

			// Add Hotel
			Hotel hotel = new Hotel();
			hotel.setHotelName("Taj Resort");
			hotel.setLocation("Goa Beach");
			hotel.setCheckInDate("2025-12-01");
			hotel.setCheckOutDate("2025-12-07");
			hotel.setTrip(trip);
			hotelRepo.save(hotel);

			// Add Expense
			Expense expense = new Expense();
			expense.setCategory("Food");
			expense.setAmount(3500);
			expense.setTrip(trip);
			expenseRepo.save(expense);

			// Add Itinerary
			Itinerary itinerary = new Itinerary();
			itinerary.setActivityName("Beach walk");
			itinerary.setActivityDate("2025-12-02");
			itinerary.setActivityTime("07:00 AM");
			itinerary.setTrip(trip);
			itineraryRepo.save(itinerary);

			// Add Feedback
			Feedback feedback = new Feedback();
			feedback.setComment("Amazing trip!");
			feedback.setRating(5);
			feedback.setTrip(trip);
			feedback.setUser(user);
			feedbackRepo.save(feedback);
		};
	}
}
