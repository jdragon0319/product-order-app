package kr.co._29cm.homework.order.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderSaveResponseTest {

    private OrderSaveResponse response;

    @BeforeEach
    void setUp() {
        response = OrderSaveResponse.of(1L,
                List.of(
                        OrderProductSaveResponse.of("[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 3),
                        OrderProductSaveResponse.of("[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 10000, 6)
                )
        );
    }

    @DisplayName("주문 상품명, 개수 출력")
    @Test
    void getOrders() {
        OrderSaveResponse response = OrderSaveResponse.of(1L,
                List.of(
                        OrderProductSaveResponse.of("[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 3),
                        OrderProductSaveResponse.of("[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 10000, 6)
                )
        );

        String result = response.getOrderInfo();

        assertThat(result).isEqualTo(
                "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2) - 3개\n" +
                        "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2) - 6개"
        );
    }

    @DisplayName("주문상품 총 가격")
    @Test
    void calculateProductPrice() {
        Integer result = response.calculateProductPrice();

        assertThat(result).isEqualTo(120000);
    }

    @DisplayName("5만원보다 큰 금액은 배송료가 추가 안된다.")
    @Test
    void calculateTotalPriceNoShippingCharge() {
        Integer result = response.calculateTotalPrice();

        assertThat(result).isEqualTo(120000);
    }

    @DisplayName("5만원보다 작은 금액은 배송료가 2500원 추가된다.")
    @Test
    void calculateTotalPrice() {
        OrderSaveResponse response = OrderSaveResponse.of(1L,
                List.of(
                        OrderProductSaveResponse.of("[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 2)
                )
        );

        Integer result = response.calculateTotalPrice();

        assertThat(result).isEqualTo(42500);
    }

}
