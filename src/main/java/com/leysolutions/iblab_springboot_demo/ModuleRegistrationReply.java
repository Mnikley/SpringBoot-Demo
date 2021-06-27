package com.leysolutions.iblab_springboot_demo;

import java.time.LocalDate;

public class ModuleRegistrationReply {

    String name;
    LocalDate expiration_date;
    Boolean is_expired;

    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getExpirationDate() { return expiration_date; }
    public void setExpirationDate(LocalDate expiration_date) { this.expiration_date = expiration_date; }
    public Boolean getExpiredState() { return is_expired; }
    public void setExpiredState(Boolean is_expired) { this.is_expired = is_expired; }
}
