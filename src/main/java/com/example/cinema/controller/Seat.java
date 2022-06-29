package com.example.cinema.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat implements Comparable<Seat> {
    private int row;
    private int column;
    private int price;
    private boolean isTaken;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.isTaken = false;
        this.price = row <= 4 ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonIgnore
    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && column == seat.column;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        return prime * row + column;
    }

    @Override
    public int compareTo(Seat o) {
        return this.row > o.row ? 1 : this.row < o.row ? -1 : Integer.compare(this.column, o.column);
    }
}
