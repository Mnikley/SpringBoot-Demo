package com.ml.springboot_demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Module {

    String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate expirationDate;

    // getters&setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expiration_date) {
        this.expirationDate = expiration_date;
    }

    // actual Class
    public Module(String name, LocalDate expirationDate) {
        super();
        this.name = name;
        this.expirationDate = expirationDate;
    }

}

