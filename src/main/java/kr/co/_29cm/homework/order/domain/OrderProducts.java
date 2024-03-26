package kr.co._29cm.homework.order.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import kr.co._29cm.homework.order.dto.OrderProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

import static kr.co._29cm.homework.order.domain.FeeConstants.DELIVERY_FEE;
import static kr.co._29cm.homework.order.domain.FeeConstants.LIMIT_CHARGED_DELIVERY_FEE;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderProducts {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    public static OrderProducts of(List<OrderProductResponse> responseList) {
        OrderProducts orderProducts = new OrderProducts();
        for (OrderProductResponse response : responseList) {
            orderProducts.add(OrderProduct.of(response.productId(), response.productName(), response.productPrice(), response.quantity()));
        }
        return orderProducts;
    }

    public Integer calculateProductPrice() {
        return orderProductList.stream()
                .map(orderProduct -> orderProduct.getPrice() * orderProduct.getQuantity())
                .reduce(Integer::sum)
                .get();
    }

    public Integer calculateTotalPrice() {
        Integer sumProductPrice = calculateProductPrice();
        if (sumProductPrice < LIMIT_CHARGED_DELIVERY_FEE) {
            return sumProductPrice + DELIVERY_FEE;
        }
        return sumProductPrice;
    }

    public void add(OrderProduct inputOrderProduct) {
        OrderProduct orderProduct = getOrderProduct(inputOrderProduct.getProductId());
        if (orderProduct != null) {
            orderProduct.plusQuantity(inputOrderProduct.getQuantity());
            return;
        }
        orderProductList.add(inputOrderProduct);
    }

    public OrderProduct getOrderProduct(Long productId) {
        for (OrderProduct orderProduct : orderProductList) {
            if (orderProduct.getProductId().equals(productId)) {
                return orderProduct;
            }
        }
        return null;
    }

    public void addOrder(Order order) {
        for (OrderProduct orderProduct : orderProductList) {
            orderProduct.addOrder(order);
        }
    }

}
