package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;

public interface UserService {
    UserDTO getCurrentUser();
    UserDTO updateUser(UpdateUserDTO updateUserDTO);
    void updateUserImage(MultipartFile image);
    boolean changePassword(String currentPassword, String newPassword);
}


