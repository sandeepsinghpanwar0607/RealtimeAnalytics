package com.analytics.reporting_engine.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.analytics.reporting_engine.dto.LoginRequest;
import com.analytics.reporting_engine.dto.LoginResponse;
import com.analytics.reporting_engine.dto.RegisterRequest;
import com.analytics.reporting_engine.entity.User;
import com.analytics.reporting_engine.repository.UserRepository;
import com.analytics.reporting_engine.security.JwtService;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        user.setAge(request.getAge());
        user.setCountry(request.getCountry());
        user.setCity(request.getCity());

        user.setRole(request.getRole());

        user.setCreatedAt(java.time.LocalDateTime.now());

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                    new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                token,
                user.getRole()
        );
    }
}