package com.example.cinema.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Cinema {

    private final int totalRows = 9;
    private final int totalColumns = 9;
    private final List<Seat> availableSeats = new ArrayList<>(totalRows * totalColumns);
    private Map<String, Seat> orderedTickets;

    public Cinema() {
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                availableSeats.add(new Seat(i, j));
            }
        }
        this.orderedTickets = new ConcurrentHashMap<>();
    }

    //@JsonGetter(value = "total_rows")
    @JsonProperty("total_rows")
    public int getTotalRows() {
        return totalRows;
    }

    @JsonProperty("total_columns")
    public int getTotalColumns() {
        return totalColumns;
    }

    @JsonProperty("available_seats")
    public List<Seat> getAvailableSeats() {

        return availableSeats;
    }
    @JsonIgnore
    public Map<String, Seat> getOrderedTickets() {
        return orderedTickets;
    }

    public void setOrderedTickets(Map<String, Seat> orderedTickets) {
        this.orderedTickets = orderedTickets;
    }
}

