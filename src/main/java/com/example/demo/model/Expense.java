package com.example.demo.model;

import jakarta.persistence.*;


@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    private String category;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    // 👇 Add this to associate expense with the user who created it
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

}
