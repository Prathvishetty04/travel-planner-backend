package com.example.demo.service;

import com.example.demo.model.Expense;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    public Expense createExpense(Expense expense) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Optional<User> currentUser = userRepository.findByUsername(currentUsername);
        if (currentUser.isPresent()) {
            Trip trip = expense.getTrip();
            if (trip != null) {
                Optional<Trip> tripFromDb = tripRepository.findById(trip.getTripId());
                if (tripFromDb.isPresent() && tripFromDb.get().getUser().getUserId().equals(currentUser.get().getUserId())) {
                    expense.setTrip(tripFromDb.get());
                    return expenseRepository.save(expense);
                }
            }
        }

        throw new RuntimeException("Unauthorized or invalid trip.");
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public void deleteExpense(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Optional<User> currentUser = userRepository.findByUsername(currentUsername);
        Optional<Expense> expense = expenseRepository.findById(id);

        if (expense.isPresent() && currentUser.isPresent()) {
            Trip trip = expense.get().getTrip();
            if (trip != null && trip.getUser().getUserId().equals(currentUser.get().getUserId())) {
                expenseRepository.deleteById(id);
                return;
            }
        }

        throw new RuntimeException("Unauthorized to delete this expense.");
    }

}
