package com.example.fitnesstracker.domain.role.dto;

import lombok.Data;

@Data
public class AddRoleToUserDto {
    private String username;
    private String rolename;
}
