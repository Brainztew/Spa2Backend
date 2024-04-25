package com.spaBackend.spaBackend.services;

import java.util.List;


import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import org.springframework.web.client.RestTemplate;
import java.util.Map;



import com.spaBackend.spaBackend.model.Booking;

@Service
public class BookingService {
    private final MongoOperations mongoOperations;

    public BookingService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
    
    public List<Booking> getAllBookings() {
        return mongoOperations.findAll(Booking.class);
    }

    public Booking addBooking(Booking booking) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookingDate = LocalDate.parse(booking.getDate(), formatter);
        if (!bookingDate.isAfter(LocalDate.now()) || booking.getTime().isEmpty() || booking.getName().isEmpty() || booking.getPhone().isEmpty() || booking.getTreatment().isEmpty()) {
            throw new IllegalArgumentException("Invalid måste ange datum, tid, namn, telefonnummer och behandling");
        }
        if ("Välj tid".equals(booking.getTime())) {
            throw new IllegalArgumentException("Invalid måste ange tid");
        }
        if("Välj behandling".equals(booking.getTreatment())) {
            throw new IllegalArgumentException("Invalid måse ange behandling");
        }
        if (isMonday(booking.getDate()) || isHoliday(booking.getDate())) {
            throw new IllegalArgumentException("Invalid måndagar är stängt och helgdagar är heliga!");
        }
        if (mongoOperations.find(Query.query(Criteria.where("date").is(booking.getDate()).and("time").is(booking.getTime()).and("treatment").is(booking.getTreatment())), Booking.class).size() > 0) {
            throw new IllegalArgumentException("Invalid tiden är redan bokad");
        }
        mongoOperations.save(booking);
        return booking;
    }

    public void deleteBooking(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        mongoOperations.remove(query, Booking.class);
    }
    
    public boolean isMonday(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    public boolean isHoliday(String date) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://sholiday.faboul.se/dagar/v2.1/2024";
        Map<String, List<Map<String, String>>> holidays = restTemplate.getForObject(url, Map.class);
        return holidays.get("dagar").stream().anyMatch(day -> day.get("datum").equals(date) && day.containsKey("helgdag"));
    }
}

