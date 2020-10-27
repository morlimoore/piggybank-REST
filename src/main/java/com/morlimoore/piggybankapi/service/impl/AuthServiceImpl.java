package com.morlimoore.piggybankapi.service.impl;

import com.morlimoore.piggybankapi.dto.AuthResponseDto;
import com.morlimoore.piggybankapi.dto.LoginUserRequestDto;
import com.morlimoore.piggybankapi.dto.RegisterUserRequestDto;
import com.morlimoore.piggybankapi.entities.NotificationEmail;
import com.morlimoore.piggybankapi.entities.Token;
import com.morlimoore.piggybankapi.entities.User;
import com.morlimoore.piggybankapi.exceptions.CustomException;
import com.morlimoore.piggybankapi.payload.ApiResponse;
import com.morlimoore.piggybankapi.repositories.TokenRepository;
import com.morlimoore.piggybankapi.repositories.UserRepository;
import com.morlimoore.piggybankapi.security.JwtTokenProvider;
import com.morlimoore.piggybankapi.service.AuthService;
import com.morlimoore.piggybankapi.service.MailService;
import com.morlimoore.piggybankapi.service.SignupTokenService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.morlimoore.piggybankapi.util.CreateResponse.createResponse;
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
    private final JwtTokenProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           SignupTokenService signupTokenService,
                           TokenRepository tokenRepository,
                           MailService mailService,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtProvider) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.signupTokenService = signupTokenService;
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    @Transactional
    public Boolean signup(RegisterUserRequestDto registerUserRequestDto) {
        User user = modelMapper.map(registerUserRequestDto, User.class);
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        user.setIsEnabled(false);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new CustomException("User with email already exists", BAD_REQUEST);
        }
        User tempUser = userRepository.getUserByEmail(user.getEmail()).get();
        String signUpToken = signupTokenService.getToken();
        Token token = new Token();
        token.setToken(signUpToken);
        token.setPurpose("SIGNUP");
        token.setIsValid(true);
        token.setUser(tempUser);
        tokenRepository.save(token);
        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(), "Thank you for signing up to PiggyBank App, please click on " +
                "below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));

        return true;

//        try {
//            User user = userRepository.getUserByEmail("email")
//                    .orElseThrow(() -> new CustomException("user with email already exists", BAD_REQUEST));
//
//            userRepository.save(registerUserRequestDto);
//
//        } catch(Exception exception) {
//            throw new CustomException(exception.getMessage(), BAD_REQUEST);
//        }
    }

    @Override
    public ResponseEntity<Object> login(LoginUserRequestDto loginUserRequestDto) {
        if (!userRepository.getUserByEmail(loginUserRequestDto.getEmail()).isPresent()) {
            ApiResponse<?> response = new ApiResponse<>(FORBIDDEN);
            response.setError("User not found");
            response.setMessage("Account does not exist");
            response.setDebugMessage("Sign up to create an account with us");
            return ResponseEntity.status(FORBIDDEN.value()).body(response);
        }

        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginUserRequestDto.getEmail(),
                    loginUserRequestDto.getPassword()
            ));

        } catch (BadCredentialsException e) {
            ApiResponse<?> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED);
            response.setError(e.getMessage());
            response.setMessage("Email or password is incorrect");
            response.setDebugMessage("Click the password reset button to reset your password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);

        } catch (DisabledException e) {
            ApiResponse<?> response = new ApiResponse<>(FORBIDDEN);
            response.setError(e.getMessage());
            response.setMessage("Please, activate your account first");
            response.setDebugMessage("Click the account verification link sent to the email address you provided on sign up.");
            return ResponseEntity.status(FORBIDDEN.value()).body(response);
        }

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        logger.info("Authenticate: " + authenticate.getName());
        String token = jwtProvider.createLoginToken(authenticate.getName());
        logger.info("Token: " + token);
        AuthResponseDto authResponseDto = new AuthResponseDto(token, loginUserRequestDto.getEmail());
        ApiResponse<AuthResponseDto> response = new ApiResponse<>(OK);
        response.setData(authResponseDto);
        return ResponseEntity.ok(response);
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
        ApiResponse<String> response = new ApiResponse<>(OK);
        response.setData("Account activated successfully");
        response.setMessage("Success");
        return createResponse(response);
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
