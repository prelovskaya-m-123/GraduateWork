package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.User;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getPk())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole())
                .image(user.getImage())
                .build();
    }
}
