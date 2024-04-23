package com.spaBackend.spaBackend.services;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.spaBackend.spaBackend.model.Booking;

public interface BookingRepository extends MongoRepository<Booking, String>{
   
    
} 
