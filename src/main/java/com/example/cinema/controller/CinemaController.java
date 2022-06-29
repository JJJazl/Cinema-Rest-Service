package com.example.cinema.controller;

import com.example.cinema.component.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
public class CinemaController {
    private CinemaBookingService cinemaBookingService;

    @Autowired
        public CinemaController(CinemaBookingService cinemaBookingService) {
        this.cinemaBookingService = cinemaBookingService;
    }

    @GetMapping("/seats")
    public Cinema getListOfAvailableSeats() {
        return cinemaBookingService.sendSeatsRequest();
    }

    @GetMapping("/booked")
    public Map<String, Collection<Seat>> getListOfBookedSeats() {
        return Map.of("ordered_seats", cinemaBookingService.sendBookedRequest());
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> buyTicket(@RequestBody Seat seat) {
        return cinemaBookingService.sendPurchaseRequest(seat);
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnTicket(@RequestBody Ticket ticket) {
        return cinemaBookingService.sendReturnRequest(ticket);
    }

    @PostMapping("/stats")
    public ResponseEntity<Object> getStatsOfCinema(@RequestParam(required = false) String password) {
        return cinemaBookingService.sendStatsRequest(password);
    }

    @PutMapping("/change_price")
    public ResponseEntity<Object> putNewPrice(@RequestParam(required = false) String password,
                                              @RequestParam(required = false) int price,
                                              @RequestBody Seat seat) {
        return cinemaBookingService.sendChangePriceRequest(password, price, seat);
    }

}
