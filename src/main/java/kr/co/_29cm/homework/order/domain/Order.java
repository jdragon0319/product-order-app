package kr.co._29cm.homework.order.domain;

import jakarta.persistence.*;
import kr.co._29cm.homework.order.dto.OrderProductResponse;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Builder
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private OrderProducts orderProducts;

    public static Order of(List<OrderProductResponse> orderProductResponses) {
        Order order = new Order();
        order.status = OrderStatus.START;
        OrderProducts orderProducts = OrderProducts.of(orderProductResponses);
        order.addOrderProductList(orderProducts);
        return order;
    }

    public static Order of(OrderProducts orderProducts) {
        Order order = new Order();
        order.status = OrderStatus.START;
        order.addOrderProductList(orderProducts);
        return order;
    }

    private void addOrderProductList(OrderProducts orderProducts) {
        orderProducts.addOrder(this);
        this.orderProducts = orderProducts;
    }


    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getTotalPrice() {
        return orderProducts.calculateTotalPrice();
    }

    public List<OrderProduct> getOrderProductList() {
        return orderProducts.getOrderProductList();
    }

    public void setId(Long id) {
        this.id = id;
    }
}
