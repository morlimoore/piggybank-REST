//package com.morlimoore.piggybankapi.apitests;
//
//import com.morlimoore.piggybankapi.PiggybankApiApplication;
//
//import org.junit.jupiter.api.BeforeEach;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import static io.restassured.RestAssured.given;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//@ActiveProfiles("test")
//@SpringBootTest(classes = PiggybankApiApplication.class)
//public class AuthenticationTest {
//
//    private final String CONTEXT_PATH = "/api/v1";
//
//    @BeforeEach
//    public void setup() {
//        RestAssured.baseURI = "https://localhost";
//        RestAssured.port = 8443;
////        request = given();
//    }
//
//    @Test
//    public void signUpTest() {
//
//        Map<String, Object> userDetails = new HashMap<>(){{
//            put("firstName", "John");
//            put("lastName", "nwaefi");
//            put("phoneNumber", "08131207309");
//            put("dateOfBirth", 1993-06-16);
//            put("createdAt", Timestamp.valueOf(LocalDateTime.now()));
//            put("email", "john_nwaefi@gmail.com");
//            put("password", "john_nwaefi");
//        }};
////        assertNotNull(userDetails);
//        Response response = given()
//                .relaxedHTTPSValidation()
//                .contentType("application/json")
//                .accept("application/json")
//                .body(userDetails)
//                .when()
//                .request("POST", CONTEXT_PATH + "/auth/signup")
//                .then()
//                .statusCode(200)
//                .contentType("application/json")
//                .extract().response();
//    }
//}
