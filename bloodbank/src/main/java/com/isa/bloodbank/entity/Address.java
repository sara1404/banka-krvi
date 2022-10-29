package com.isa.bloodbank.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    String street;
    int number;
    String city;
    int zipcode;
    String country;
    double longitude;
    double latitude;
}
