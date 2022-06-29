package com.example.cinema.component;

import com.example.cinema.controller.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {

    private String token;
    private Seat seat;

    public Ticket(Seat seat) {
        this.seat = seat;
        this.token = UUID.randomUUID().toString();
    }

    public Ticket(@JsonProperty("token") String token) {
        this.token = token;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("ticket")
    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

}
