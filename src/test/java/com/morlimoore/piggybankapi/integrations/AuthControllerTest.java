//package com.morlimoore.piggybankapi.integrations;
//
//import com.morlimoore.piggybankapi.dto.RegisterUserRequestDTO;
//import com.morlimoore.piggybankapi.entities.User;
//import com.morlimoore.piggybankapi.repositories.UserRepository;
//import com.morlimoore.piggybankapi.security.JwtTokenProvider;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.sql.Date;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//import static com.morlimoore.SetUp.TestUtils.asJsonString;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//public class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    Date dob = Date.valueOf("2000-05-17");
//
//    RegisterUserRequestDTO registerUserRequestDto = new RegisterUserRequestDTO();
//
//    RegisterUserRequestDTO registerUserRequestDto2 = new RegisterUserRequestDTO();
//
//    @BeforeAll
//    public void setUp() {
//        registerUserRequestDto.setEmail("example@gmail.com");
//        registerUserRequestDto.setPassword("password");
//        registerUserRequestDto.setFirstName("John");
//        registerUserRequestDto.setLastName("Doe");
//        registerUserRequestDto.setPhoneNumber("08127334675");
//        registerUserRequestDto.setDateOfBirth(dob);
//
//        registerUserRequestDto2.setEmail("example2@gmail.com");
//        registerUserRequestDto2.setPassword("password");
//        registerUserRequestDto2.setFirstName("Nick");
//        registerUserRequestDto2.setLastName("Doe");
//        registerUserRequestDto2.setPhoneNumber("08127334675");
//        registerUserRequestDto2.setDateOfBirth(dob);
//
//
//        User user = modelMapper.map(registerUserRequestDto2, User.class);
//        user.setRole("USER");
//        user.setIsEnabled(true);
//
//        userRepository.save(user);
//    }
//
//    @Test
//    @DisplayName("registers a user successfully - integrations ❄️")
//    public void signUp() throws Exception {
//        //String token = jwtTokenProvider.createLoginToken("");
//
//        mockMvc.perform(post("/auth/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                //.header("Authorization", token)
//                .content(asJsonString(registerUserRequestDto)))
//                .andExpect(status().isOk()).andDo(print());
//    }
//
//    public void signUpWhenEmailAlreadyExist() {
//        try {
//
//            mockMvc.perform(post("/auth/signup")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    //.header("Authorization", token)
//                    .content(asJsonString(registerUserRequestDto)))
//                    .andExpect(status().isBadRequest()).andDo(print());
//
//        } catch(Exception exception) {
//            assertEquals("user with email already exist", exception.getMessage());
//        }
//    }
//}
