package kr.co._29cm.homework.order.domain;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@Builder
@Table(name = "order_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;

    public static OrderProduct of(Long productId, String productName, Integer price, Integer quantity) {
        return OrderProduct.builder()
                .productId(productId)
                .productName(productName)
                .price(price)
                .quantity(quantity)
                .build();
    }

    public void addOrder(Order order) {
        this.order = order;
    }

    public void plusQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}
