package com.snackhuborder.infrastructure.api.controllers;

import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.models.CreateOrderRequest;
import com.snackhuborder.infrastructure.order.models.UpdateStatusRequest;
import com.snackhuborder.infrastructure.util.OrderHelper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.mockito.ArgumentMatchers.any;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/clean.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class OrderControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class RegisterOrder {

        @Test
        void shouldSaveOrder() throws Exception {
            CreateOrderRequest request = OrderHelper.getOrderRequest();

            given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/orders/createOrder")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("items"))
                .body("$", hasKey("orderIdentifier"))
                .body("$", hasKey("observation"))
                .body("$", hasKey("status"));
        }

        @Test
        void shouldThrowException_whenSaveOrder_EmptyItems() throws Exception {
            CreateOrderRequest request = OrderHelper.getOrderRequestWithoutItems();

            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post("/orders/createOrder")
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }

    @Nested
    class FindOrder {

        @Test
        @Sql(scripts = {"/clean.sql",
                "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void shouldListAllOrders() throws Exception {
            given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/orders/list")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/OrdersSchema.json"));
        }

        @Test
        @Sql(scripts = {"/clean.sql",
                "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void shouldListOrdersByStatus() throws Exception {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/orders/{status}", OrderStatus.RECEIVED)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/OrdersSchema.json"));
        }
    }

    @Nested
    class UpdateOrder {

        @Test
        @Sql(scripts = {"/clean.sql",
                "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void shouldUpdateOrder() {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new UpdateStatusRequest(OrderStatus.FINISHED))
                    .when()
                    .put("/orders/{id}", 1L)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body(matchesJsonSchemaInClasspath("schemas/OrderSchema.json"));
        }

        @Test
        @Sql(scripts = {"/clean.sql",
                "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void shouldNotUpdateOrder_whenNotExistSavedOrder() {
            given()
                    .filter(new AllureRestAssured())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new UpdateStatusRequest(OrderStatus.FINISHED))
                    .when()
                    .put("/orders/{id}", 10L)
                    .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
    }
}
