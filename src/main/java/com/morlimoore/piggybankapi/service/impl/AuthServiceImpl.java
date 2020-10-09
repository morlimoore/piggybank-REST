package com.morlimoore.piggybankapi.service.impl;

import com.morlimoore.piggybankapi.dto.RegisterUserRequestDto;
import com.morlimoore.piggybankapi.entities.NotificationEmail;
import com.morlimoore.piggybankapi.entities.Token;
import com.morlimoore.piggybankapi.entities.User;
import com.morlimoore.piggybankapi.exceptions.CustomException;
import com.morlimoore.piggybankapi.repositories.TokenRepository;
import com.morlimoore.piggybankapi.repositories.UserRepository;
import com.morlimoore.piggybankapi.service.AuthService;
import com.morlimoore.piggybankapi.service.MailService;
import com.morlimoore.piggybankapi.service.SignupTokenService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final SignupTokenService signupTokenService;
    private final TokenRepository tokenRepository;
    private final MailService mailService;

    public AuthServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           SignupTokenService signupTokenService,
                           TokenRepository tokenRepository,
                           MailService mailService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.signupTokenService = signupTokenService;
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public void signup(RegisterUserRequestDto registerUserRequestDto) {
        User user = modelMapper.map(registerUserRequestDto, User.class);
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        user.setIs_active(false);
        userRepository.save(user);
        User tempUser = userRepository.getUserByEmail(user.getEmail()).get();
        String signUpToken = signupTokenService.getToken();
        Token token = new Token();
        token.setToken(signUpToken);
        token.setPurpose("SIGNUP");
        token.setIs_valid(true);
        token.setUser(tempUser);
        tokenRepository.save(token);
        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(), "Thank you for signing up to PiggyBank App, please click on " +
                "below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    @Override
    public void verifyAccount(String token) {
        Optional<Token> verificationToken = tokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new CustomException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    public void fetchUserAndEnable(Token token) {
        String email = token.getUser().getEmail();
        User tempUser = userRepository.getUserByEmail(email).orElseThrow(() -> new CustomException("User not found with email - " + email));
        tempUser.setIs_active(true);
        userRepository.save(tempUser);
    }
}
