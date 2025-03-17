package com.company.service;

import com.company.domain.User;
import com.company.dto.LoginRequest;
import com.company.dto.SignupRequest;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 회원가입 처리
    public void register(SignupRequest signupRequest) {
        Optional<User> existingUser = userRepository.findByUsername(signupRequest.getUsername());
        if(existingUser.isPresent()){
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    // 로그인 처리 및 토큰 생성 (더미 토큰 반환)
    public String authenticate(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
                // 실제 환경에서는 JWT 라이브러리로 토큰 생성
                return "dummy-jwt-token-for-" + user.getUsername();
            } else {
                throw new RuntimeException("비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }
}
