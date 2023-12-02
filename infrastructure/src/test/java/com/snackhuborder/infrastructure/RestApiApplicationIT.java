package com.snackhuborder.infrastructure;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApiApplicationIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() throws Exception {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void shouldStartApplication() {
        given()
                .when()
                .get("/actuator/health")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("status", equalTo("UP"));
        ;
    }
}