package com.snackhuborder.infrastructure.order.persistence;

import com.snackhuborder.domain.order.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class OrderRepositoryIT {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldCreateTables() {
        assertThat(orderRepository.count()).isEqualTo(2);
    }

    @Test
    void shouldFindAllOrder() {
        var result = orderRepository.findAll();
        assertThat(result)
                .hasSize(2);
    }

    @Test
    void shouldFindAllOrderByStatusAndCreatedDate() {
        var status = Set.of(OrderStatus.RECEIVED);
        var result = this.orderRepository.findAllOrderByStatusAndCreatedDate(status);
        assertThat(result)
                .hasSize(1);
    }

}
