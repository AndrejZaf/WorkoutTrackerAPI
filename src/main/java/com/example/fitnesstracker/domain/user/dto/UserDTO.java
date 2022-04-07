package com.example.fitnesstracker.domain.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private String email;

    private UUID uid;

    private Integer height;

    private Integer weight;

    private String measurementSystem;

    private String imageUrl;
}
