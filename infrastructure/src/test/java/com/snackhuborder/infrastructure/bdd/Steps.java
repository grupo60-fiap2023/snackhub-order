package com.snackhuborder.infrastructure.bdd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.models.CreateOrderRequest;
import com.snackhuborder.infrastructure.order.models.OrderResponse;
import com.snackhuborder.infrastructure.order.models.UpdateStatusRequest;
import com.snackhuborder.infrastructure.util.OrderHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Steps {

    private Response response;

    private OrderResponse orderResponse;

    private String ENDPOINT_MENSAGENS = "http://127.0.0.1:8080/orders/";

    @Quando("submeter uma requisição de pedido")
    public OrderResponse orderRequest() {
        CreateOrderRequest request = OrderHelper.getOrderRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(ENDPOINT_MENSAGENS + "createOrder");
        return response.then().extract().as(OrderResponse.class);
    }

    @Então("o pedido é registrado com sucesso")
    public void orderCreateWithSucess() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/OrderSchema.json"));
    }

    @Dado("que um pedido já foi registrado")
    public void orderAlreadyProcess() {
        orderResponse = orderRequest();
    }

    @Quando("requisitar a lista de pedidos")
    public void findOrders() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(ENDPOINT_MENSAGENS+ "list");
    }

    @Então("os pedidos são exibidas com sucesso")
    public void orderShowWithSucess() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/OrdersSchema.json"));
    }

    @Quando("requisitar a alteração do pedido")
    public void updateRequest() throws JsonProcessingException {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMapper().writeValueAsString(new UpdateStatusRequest(OrderStatus.FINISHED)))
                .when()
                .put(ENDPOINT_MENSAGENS+ "{id}", orderResponse.id().toString());
    }

    @Então("o pedido é atualizada com sucesso")
    public void orderUpdatedWithSucess() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/OrderSchema.json"));
    }
}
