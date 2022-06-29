package com.example.cinema.component;

import com.example.cinema.controller.Cinema;
import com.example.cinema.controller.Seat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SummaryStats {
    private int currentIncome;
    private int numberOfAvailableSeats;
    private int numberOfPurchasedTickets;
    @JsonIgnore
    private Cinema cinema;

    @Autowired
    public SummaryStats(Cinema cinema) {
        this.cinema = cinema;
    }

    @JsonProperty("current_income")
    public int getCurrentIncome() {
        return currentIncome = cinema.getOrderedTickets().values().stream()
                .map(Seat::getPrice)
                .reduce(0, Integer::sum, (a, b) -> a + b);
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    @JsonProperty("number_of_available_seats")
    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats = cinema.getAvailableSeats().size();
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    @JsonProperty("number_of_purchased_tickets")
    public int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets = cinema.getOrderedTickets().values().size();
    }

    public void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }
}
