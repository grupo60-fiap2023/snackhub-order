package com.snackhuborder.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snackhuborder.application.order.create.CreateOrderUseCase;
import com.snackhuborder.application.order.retrieve.FindAllOrdersUseCase;
import com.snackhuborder.application.order.retrieve.FindOrdersByStatusUseCase;
import com.snackhuborder.application.order.update.UpdateOrderStatusUseCase;
import com.snackhuborder.domain.order.Order;
import com.snackhuborder.domain.order.OrderGateway;
import com.snackhuborder.domain.order.OrderId;
import com.snackhuborder.domain.order.OrderStatus;
import com.snackhuborder.infrastructure.order.models.api.CreateOrderRequest;
import com.snackhuborder.infrastructure.order.models.api.UpdateStatusRequest;
import com.snackhuborder.infrastructure.util.OrderHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderGateway orderGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        OrderController orderController = new OrderController(new CreateOrderUseCase(orderGateway),
                new FindAllOrdersUseCase(orderGateway),
                new FindOrdersByStatusUseCase(orderGateway),
                new UpdateOrderStatusUseCase(orderGateway));
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class RegisterOrder {

        @Test
        void shouldSaveOrder() throws Exception {
            CreateOrderRequest request = OrderHelper.getOrderRequest();

            when(orderGateway.create(any(Order.class)))
                    .thenAnswer(i -> i.getArgument(0));

            mockMvc.perform(post("/orders/createOrder")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(request)))
                    .andExpect(status().isCreated());
            verify(orderGateway, times(1))
                    .create(any(Order.class));
        }

        @Test
        void shouldThrowException_whenSaveOrder_EmptyItems() throws Exception {
            CreateOrderRequest request = OrderHelper.getOrderRequestWithoutItems();

            when(orderGateway.create(any(Order.class)))
                    .thenAnswer(i -> i.getArgument(0));

            mockMvc.perform(post("/orders/createOrder")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(request)))
                    .andExpect(status().isUnprocessableEntity());
            verify(orderGateway,  never())
                    .create(any(Order.class));
        }

        @Test
        void shouldThrowException_whenSaveOrder_InvalidItem() throws Exception {
            CreateOrderRequest request = OrderHelper.getOrderRequestWithouMandadoryItemField();

            when(orderGateway.create(any(Order.class)))
                    .thenAnswer(i -> i.getArgument(0));

            mockMvc.perform(post("/orders/createOrder")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(request)))
                    .andExpect(status().isUnprocessableEntity());
            verify(orderGateway,  never())
                    .create(any(Order.class));
        }
    }

    @Nested
    class FindOrder {

        @Test
        void shouldListAllOrders() throws Exception {
            Order order = OrderHelper.getOrderMock();
            List<Order> orders = Arrays.asList(order);
            when(orderGateway.findAllOrdersByStatus(anySet()))
                    .thenReturn(orders);

            mockMvc.perform(get("/orders/list")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(order.getId().getValue()))
                    .andExpect(jsonPath("$[0].orderIdentifier").value(order.getOrderIdentifier()))
                    .andExpect(jsonPath("$[0].observation").value(order.getObservation()))
                    .andExpect(jsonPath("$[0].status").exists());
            ;

            verify(orderGateway, times(1))
                    .findAllOrdersByStatus(anySet());
        }

        @Test
        void shouldListOrdersByStatus() throws Exception {
            Order order = OrderHelper.getOrderMock();
            List<Order> orders = Arrays.asList(order);
            when(orderGateway.findOrdersByStatus(any(OrderStatus.class)))
                    .thenReturn(orders);

            mockMvc.perform(get("/orders/{status}", order.getStatus())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(order.getId().getValue()))
                    .andExpect(jsonPath("$[0].orderIdentifier").value(order.getOrderIdentifier()))
                    .andExpect(jsonPath("$[0].observation").value(order.getObservation()))
                    .andExpect(jsonPath("$[0].status").exists());

            verify(orderGateway, times(1))
                    .findOrdersByStatus(any(OrderStatus.class));
        }
    }

    @Nested
    class UpdateOrder {

        @Test
        void shouldUpdateOrder() throws Exception {
            Order order = OrderHelper.getOrderMock();
            when(orderGateway.update(any(Order.class)))
                    .thenReturn(order);

            when(orderGateway.findOrderById(any(OrderId.class)))
                    .thenReturn(Optional.of(order));

            mockMvc.perform(put("/orders/{id}", order.getId().getValue())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(new UpdateStatusRequest(OrderStatus.FINISHED))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("id").value(order.getId().getValue()))
                    .andExpect(jsonPath("orderIdentifier").value(order.getOrderIdentifier()))
                    .andExpect(jsonPath("observation").value(order.getObservation()))
                    .andExpect(jsonPath("status").value(OrderStatus.FINISHED.getName()));;

            verify(orderGateway, times(1))
                    .update(any(Order.class));
        }

        @Test
        void shouldNotUpdateOrder_whenNotExistSavedOrder() throws Exception {
            Order order = OrderHelper.getOrderMock();

            when(orderGateway.findOrderById(any(OrderId.class)))
                    .thenReturn(Optional.empty());

            mockMvc.perform(put("/orders/{id}", order.getId().getValue())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(new UpdateStatusRequest(OrderStatus.FINISHED))))
                    .andExpect(status().isUnprocessableEntity());

            verify(orderGateway, times(1))
                    .findOrderById(any(OrderId.class));

            verify(orderGateway, never())
                    .update(any(Order.class));
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
