package com.snackhuborder.infrastructure.order.persistence;

import com.snackhuborder.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<OrderJpaEntity, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = "items")
    List<OrderJpaEntity> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = "items")
    @Query("SELECT ord FROM Order ord WHERE ord.orderStatus in :statusList ORDER BY ord.orderStatus desc, ord.createdAt asc")
    List<OrderJpaEntity> findAllOrderByStatusAndCreatedDate(Set<OrderStatus> statusList);
}
