package com.leysolutions.iblab_springboot_demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Module {

    String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate expiration_date;
    Boolean is_expired;

    // getters&setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpirationDate() { return expiration_date; }
    public void setExpirationDate(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Boolean getExpiredState() { return is_expired; }
    public void setExpiredState(Boolean is_expired) { this.is_expired = is_expired; }

    // actual Class
    public Module(String name, LocalDate expiration_date, Boolean is_expired) {
        super();
        this.name = name;
        this.expiration_date = expiration_date;
        this.is_expired = is_expired;
    }

}

