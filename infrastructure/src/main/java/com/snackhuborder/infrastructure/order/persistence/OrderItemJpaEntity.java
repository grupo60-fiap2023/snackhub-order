package com.snackhuborder.infrastructure.order.persistence;


import com.snackhuborder.domain.order.OrderItem;
import com.snackhuborder.domain.order.OrderItemCategory;
import com.snackhuborder.domain.order.OrderItemId;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity(name = "OrderItem")
@Table(name = "order_items")
public class OrderItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "category", nullable = false)
    @Convert(converter = OrderItemCategoryConverter.class)
    private OrderItemCategory category;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderJpaEntity orderJpaEntity;

    public OrderItemJpaEntity() {
    }

    public OrderItemJpaEntity(Long id, String name, BigDecimal price, Integer quantity, OrderItemCategory category, OrderJpaEntity orderJpaEntity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.orderJpaEntity = orderJpaEntity;
    }
    public OrderItemJpaEntity(String name, BigDecimal price, Integer quantity, OrderItemCategory category, OrderJpaEntity orderJpaEntity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.orderJpaEntity = orderJpaEntity;
    }

    public static OrderItemJpaEntity create(final OrderItem orderItem, final OrderJpaEntity orderJpaEntity) {
        return new OrderItemJpaEntity(
                orderItem.getName(),
                orderItem.getPrice(),
                orderItem.getQuantity(),
                orderItem.getCategory(),
                orderJpaEntity);

    }
    public static OrderItemJpaEntity from(final OrderItem orderItem,  final OrderJpaEntity orderJpaEntity) {
        return new OrderItemJpaEntity(
                orderItem.getId().getValue(),
                orderItem.getName(),
                orderItem.getPrice(),
                orderItem.getQuantity(),
                orderItem.getCategory(),
                orderJpaEntity);
    }

    public OrderItem toAggregate() {
        return OrderItem.with(OrderItemId.from(getId()), getName(), getPrice(), getQuantity(), getCategory());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderItemCategory getCategory() {
        return category;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
