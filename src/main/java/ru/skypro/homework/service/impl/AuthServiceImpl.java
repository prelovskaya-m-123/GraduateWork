package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.ComplexMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public boolean login(String userName, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean register(Register register) {
        if (userRepository.existsByEmail(register.getUsername())) {
            return false;
        }

        User user = new User();
        user.setEmail(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setPhone(register.getPhone());
        user.setRole(register.getRole() != null ? register.getRole() : Role.USER);

        userRepository.save(user);
        return true;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

}
