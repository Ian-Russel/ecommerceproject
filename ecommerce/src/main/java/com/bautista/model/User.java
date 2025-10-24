package com.bautista.model;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String role;
    private Boolean isActive;
    private Boolean isEmailVerified;
    private Date createdAt;
    private Date lastLogin;
}