package com.example.auth.service;

import com.example.repositoryservice.entity.UserEntity;
import com.example.repositoryservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    public UserEntity getUserByEmail(String email){
        return this.userRepository.fetchUserWithRole(email)
                .orElseThrow();
    }

}
