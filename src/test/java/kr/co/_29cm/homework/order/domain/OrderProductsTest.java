package kr.co._29cm.homework.order.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderProductsTest {

    private OrderProducts orderProducts;

    @BeforeEach
    void setUp() {
        List<OrderProduct> orderProductList = List.of(
                OrderProduct.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 3),
                OrderProduct.of(2L, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 10000, 6)
        );
        orderProducts = new OrderProducts(orderProductList);
    }

    @DisplayName("동일한 상품을 주문하면 수량을 증가시킨다.")
    @Test
    void add() {
        orderProducts.add(OrderProduct.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 3));

        OrderProduct orderProduct = orderProducts.getOrderProduct(1L);
        assertThat(orderProduct.getQuantity()).isEqualTo(6);
    }

    @DisplayName("주문상품 총 가격")
    @Test
    void calculateProductPrice() {
        Integer result = orderProducts.calculateProductPrice();

        assertThat(result).isEqualTo(120000);
    }

    @DisplayName("5만원보다 큰 금액은 배송료가 추가 안된다.")
    @Test
    void calculateTotalPriceNoShippingCharge() {
        Integer result = orderProducts.calculateTotalPrice();

        assertThat(result).isEqualTo(120000);
    }

    @DisplayName("5만원보다 작은 금액은 배송료가 2500원 추가된다.")
    @Test
    void calculateTotalPrice() {
        List<OrderProduct> orderProductList = List.of(
                OrderProduct.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 2)
        );
        OrderProducts orderProducts = new OrderProducts(orderProductList);
        Integer result = orderProducts.calculateTotalPrice();

        assertThat(result).isEqualTo(42500);
    }

    @DisplayName("주문상품 수량이 상품 재고보다 많으면 예외 발생")
    @Test
    void getOrderProduct() {
        OrderProduct orderProduct = orderProducts.getOrderProduct(1L);

        assertThat(orderProduct.getProductId()).isEqualTo(1L);
    }

}
