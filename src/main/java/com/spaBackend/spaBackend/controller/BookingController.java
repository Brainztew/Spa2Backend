package com.spaBackend.spaBackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spaBackend.spaBackend.model.Booking;
import com.spaBackend.spaBackend.services.BookingService;

@CrossOrigin 
@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> payload) {
        String password = payload.get("password");
        String adminPassword = "admin";
        if (adminPassword.equals(password)) {
            return ResponseEntity.ok("admin-token");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
    }

    @GetMapping
    public List<Booking> getRoot() {
        return bookingService.getAllBookings();
    }

    @PostMapping
    public Booking addBooking(@RequestBody Booking booking) {
        return bookingService.addBooking(booking);
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable String id, @RequestHeader("Authorization") String token){
        String adminToken = "Bearer admin-token";
        if (!adminToken.equals(token)) { 
            throw new IllegalArgumentException("Invalid token");
        }
        bookingService.deleteBooking(id);
        return "{'meddelande': 'booking raderad: " + id + " !'}";
    }
}
