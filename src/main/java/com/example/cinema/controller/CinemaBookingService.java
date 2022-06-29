package com.example.cinema.controller;

import com.example.cinema.component.SummaryStats;
import com.example.cinema.component.Ticket;
import com.example.cinema.exception.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CinemaBookingService {

    private Cinema cinema;
    private SummaryStats summaryStats;

    @Autowired
    public CinemaBookingService(Cinema cinema, SummaryStats summaryStats) {
        this.cinema = cinema;
        this.summaryStats = summaryStats;
    }

    //СОРТИРОВАТЬ ЭЛЕМЕНТЫ
    public Cinema sendSeatsRequest() {
        return cinema;
    }

    public synchronized ResponseEntity<Object> sendPurchaseRequest(Seat seat) {
        if (seat.getRow() < 1 || seat.getRow() > cinema.getTotalRows() || seat.getColumn() < 1 ||
                seat.getColumn() > cinema.getTotalColumns()) {
            return new ResponseEntity<>(new RequestException("The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (!cinema.getAvailableSeats().contains(seat)) {
            return new ResponseEntity<>(new RequestException("The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }
        int indexOfCurrentSeat = cinema.getAvailableSeats().indexOf(seat);
        seat.setPrice(cinema.getAvailableSeats().get(indexOfCurrentSeat).getPrice());
        seat.setTaken(true);
        cinema.getAvailableSeats().remove(seat);

        Ticket ticket = new Ticket(seat);
        cinema.getOrderedTickets().put(ticket.getToken(), seat);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    public synchronized ResponseEntity<Object> sendReturnRequest(Ticket ticket) {
        if (cinema.getOrderedTickets().containsKey(ticket.getToken())) {
            ResponseEntity<Object> answer = new ResponseEntity<>(Map.of("returned_ticket",
                    cinema.getOrderedTickets().get(ticket.getToken())), HttpStatus.OK);
            Seat returnedSeat = cinema.getOrderedTickets().remove(ticket.getToken());
            returnedSeat.setTaken(false);
            cinema.getAvailableSeats().add(returnedSeat);
            Collections.sort(cinema.getAvailableSeats());
            return answer;
        }
        return new ResponseEntity<>(new RequestException("Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> sendStatsRequest(String password) {
        if (password == null || !password.equals("super_secret")) {
            return new ResponseEntity<>(new RequestException("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(summaryStats,HttpStatus.OK);
    }

    public Collection<Seat> sendBookedRequest() {
        return cinema.getOrderedTickets().values().stream()
                .sorted().collect(Collectors.toList());
    }

    public ResponseEntity<Object> sendChangePriceRequest(String password, int price, Seat seat) {
        if (password == null || !password.equals("super_secret")) {
            return new ResponseEntity<>(new RequestException("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        if (price < 1 || seat.getPrice() == price) {
            return new ResponseEntity<>(new RequestException("The price is not valid!"), HttpStatus.BAD_REQUEST);
        }
        if (seat.getRow() < 1 || seat.getRow() > cinema.getTotalRows() || seat.getColumn() < 1 ||
                seat.getColumn() > cinema.getTotalColumns()) {
            return new ResponseEntity<>(new RequestException("The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (cinema.getAvailableSeats().stream().anyMatch(i -> i.hashCode() == seat.hashCode())) {
            int indexOfCurrentSeat = cinema.getAvailableSeats().indexOf(seat);
            seat.setPrice(price);
            cinema.getAvailableSeats().set(indexOfCurrentSeat, seat);
            return new ResponseEntity<>(Map.of("Changed seat!", seat), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RequestException("The seat is ordered!"), HttpStatus.BAD_REQUEST);
    }
}


