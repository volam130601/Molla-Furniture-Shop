package com.molla.dto;

import lombok.Data;

import java.util.Date;
@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private Date lastLogin;

}
