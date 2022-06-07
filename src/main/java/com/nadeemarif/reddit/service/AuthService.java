package com.nadeemarif.reddit.service;

import com.nadeemarif.reddit.dto.RegisterRequest;
import com.nadeemarif.reddit.exceptions.SpringRedditException;
import com.nadeemarif.reddit.model.NotificationEmail;
import com.nadeemarif.reddit.model.User;
import com.nadeemarif.reddit.model.VerificationToken;
import com.nadeemarif.reddit.repository.UserRepository;
import com.nadeemarif.reddit.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void signUp(RegisterRequest request){
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .created(Instant.now())
                .enabled(false)
                .build();

        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(
                NotificationEmail.builder()
                        .recipient(user.getEmail())
                        .subject("Spring Reddit Activation")
                        .body("Thank you for signing up for Spring Reddit, " +
                                "please click on the url below to activate your account : " +
                                "http://localhost:8080/api/auth/accountVerification/" +
                                token
                        )
                .build());
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken);
    }

    private void fetchUserAndEnable(Optional<VerificationToken> verificationToken) {
    }
}
