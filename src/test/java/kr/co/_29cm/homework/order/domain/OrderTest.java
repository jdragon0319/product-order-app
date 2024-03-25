package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.order.dto.OrderProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @DisplayName("OrderProductResponse 목록으로 주문생성")
    void createByOrderProductResponse() {
        List<OrderProductResponse> orderProductList = List.of(
                OrderProductResponse.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 3),
                OrderProductResponse.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 6)
        );
        Order order = Order.of(orderProductList);

        assertThat(order.getOrderProductList().size()).isEqualTo(1);
    }

    @DisplayName("OrderProduct 목록으로 주문생성")
    @Test
    void createByOrderProduct() {
        List<OrderProduct> orderProductList = List.of(
                OrderProduct.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 3),
                OrderProduct.of(2L, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 10000, 6)
        );
        Order order = Order.of(new OrderProducts(orderProductList));

        assertThat(order.getStatus()).isEqualTo(OrderStatus.START);
    }

    @DisplayName("주문상품들 총 가격 계산")
    @Test
    void totalPrice() {
        List<OrderProduct> orderProductList = List.of(
                OrderProduct.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 3),
                OrderProduct.of(2L, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 10000, 6)
        );
        Order order = Order.of(new OrderProducts(orderProductList));
        Integer result = order.getTotalPrice();

        assertThat(result).isEqualTo(120000);
    }

    @DisplayName("상품번호가 같은 주문상품은 개수를 합한다.")
    @Test
    void addDuplicate() {
        List<OrderProductResponse> orderProductList = List.of(
                OrderProductResponse.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 3),
                OrderProductResponse.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 6)
        );
        Order order = Order.of(orderProductList);

        OrderProduct orderProduct = order.getOrderProductList().get(0);
        assertThat(orderProduct.getQuantity()).isEqualTo(9);
    }

}
