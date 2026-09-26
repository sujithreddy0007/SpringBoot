package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Street name must be atleast 5 characters")
    private String street;

    @NotBlank
    @Size(min = 5, message = "building name must be atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 5, message = "city name must be atleast 4 characters")
    private String city;

    @NotBlank
    @Size(min = 5, message = "state name must be atleast 2 characters")
    private String state;

    @NotBlank
    @Size(min = 5, message = "country name must be atleast 2 characters")
    private String country;

    @NotBlank
    @Size(min = 5, message = "pincode name must be atleast 6 characters")
    private String pincode;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String street, String buildingName, String city, String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
}
