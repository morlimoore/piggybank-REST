package com.morlimoore.piggybankapi.service.impl;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.morlimoore.piggybankapi.dto.LoginUserRequestDTO;
import com.morlimoore.piggybankapi.dto.RegisterUserRequestDTO;
import com.morlimoore.piggybankapi.entities.Token;
import com.morlimoore.piggybankapi.entities.User;
import com.morlimoore.piggybankapi.entities.UserDetailsImpl;
import com.morlimoore.piggybankapi.exceptions.CustomException;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import com.morlimoore.piggybankapi.payload.JwtResponse;
import com.morlimoore.piggybankapi.repositories.TokenRepository;
import com.morlimoore.piggybankapi.repositories.UserRepository;
import com.morlimoore.piggybankapi.security.JwtUtils;
import com.morlimoore.piggybankapi.service.AuthService;
import com.morlimoore.piggybankapi.service.MailService;
import com.morlimoore.piggybankapi.service.SignupTokenService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.morlimoore.piggybankapi.util.CreateResponse.*;
import static com.morlimoore.piggybankapi.util.RoleEnum.ROLE_USER;
import static org.springframework.http.HttpStatus.*;

@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final SignupTokenService signupTokenService;
    private final TokenRepository tokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    public AuthServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           SignupTokenService signupTokenService,
                           TokenRepository tokenRepository,
                           MailService mailService,
                           AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.signupTokenService = signupTokenService;
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<String>> signup(RegisterUserRequestDTO registerUserRequestDto) throws UnirestException {
        User user = modelMapper.map(registerUserRequestDto, User.class);
        user.setRole(ROLE_USER);
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        user.setIsEnabled(false);
        User res = null;
        try {
            res = userRepository.save(user);
        } catch (Exception e) {
            throw new CustomException("User with email already exists", BAD_REQUEST);
        }
        String signUpToken = signupTokenService.getToken();
        Token token = new Token();
        token.setToken(signUpToken);
        token.setPurpose("SIGNUP");
        token.setIsValid(true);
        token.setUser(res);
        tokenRepository.save(token);
        mailService.sendActivationMail(res.getEmail(), token.getToken());
        return successResponse("User Registration Successful", CREATED);
    }

    @Override
    public ResponseEntity<?> login(LoginUserRequestDTO loginUserRequestDto) {
        if (!userRepository.getUserByEmail(loginUserRequestDto.getEmail()).isPresent())
            return errorResponse("Account does not exist. Sign up to create an account with us", FORBIDDEN);

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginUserRequestDto.getEmail(),
                    loginUserRequestDto.getPassword()
            ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwt = jwtUtils.generateJwtToken(authenticate);

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()).get(0);

        ApiResponse<JwtResponse> response = new ApiResponse<>();
        response.setStatus(OK);
        response.setMessage("SUCCESS");
        response.setResult(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                role));
        return createResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> verifyAccount(String token) {
        Optional<Token> verificationToken = tokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new CustomException("Invalid token", BAD_REQUEST));
        if (verificationToken.get().getIsValid()) {
            fetchUserAndEnable(verificationToken.get());
            fetchTokenAndInvalidate(verificationToken.get().getToken());
        } else {
            throw new CustomException("Token has been used", BAD_REQUEST);
        }
        return successResponse("Account activated successfully", OK);
    }

    @Transactional
    public void fetchUserAndEnable(Token token) {
        String email = token.getUser().getEmail();
        User tempUser = userRepository.getUserByEmail(email).orElseThrow(() ->
                new CustomException("User not found with email - " + email, BAD_REQUEST));
        tempUser.setIsEnabled(true);
        userRepository.save(tempUser);
    }

    @Transactional
    public void fetchTokenAndInvalidate(String token) {
        Optional<Token> verificationToken = tokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new CustomException("Invalid token", BAD_REQUEST));
        verificationToken.get().setIsValid(false);
        tokenRepository.save(verificationToken.get());
    }
}
