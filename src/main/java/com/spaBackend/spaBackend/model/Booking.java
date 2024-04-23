package com.spaBackend.spaBackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;
    private String date;
    private String treatment;
    private String time;
    private String name;
    private String phone;

    
    public Booking(String id, String date, String treatment, String time, String name, String phone) {
        this.id = id;
        this.date = date;
        this.treatment = treatment;
        this.time = time;
        this.name = name;
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public String getTreatment() {
        return treatment;
    }


    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }


    public String getTime() {
        return time;
    }


    public void setTime(String time) {
        this.time = time;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }
}


