package ru.skypro.homework.dto;

import lombok.Data;
import lombok.Builder;


@Data
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
}
