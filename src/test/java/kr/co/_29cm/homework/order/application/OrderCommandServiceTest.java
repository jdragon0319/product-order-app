package kr.co._29cm.homework.order.application;

import kr.co._29cm.homework.order.domain.FakeOrderRepository;
import kr.co._29cm.homework.order.dto.OrderProductResponse;
import kr.co._29cm.homework.order.dto.OrderSaveRequest;
import kr.co._29cm.homework.order.dto.OrderSaveResponse;
import kr.co._29cm.homework.order.exception.EmptyOrderProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class OrderCommandServiceTest {

    private final FakeOrderRepository orderRepository = new FakeOrderRepository();
    private final OrderCommandService orderCommandService = new OrderCommandService(orderRepository);

    @BeforeEach
    void setUp() {
        orderRepository.clear();
    }

    @DisplayName("주문한 상품이 없으면 예외를 발생시킨다.")
    @Test
    void saveOrderNotOrderProduct() {
        OrderSaveRequest request = OrderSaveRequest.of(Collections.emptyList());

        assertThatExceptionOfType(EmptyOrderProductException.class)
                .isThrownBy(() -> orderCommandService.saveOrder(request));
    }

    @DisplayName("주문 생성")
    @Test
    void saveOrderSuccess() {
        OrderSaveRequest request = OrderSaveRequest.of(
                List.of(
                        OrderProductResponse.of(1L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 20000, 3),
                        OrderProductResponse.of(2L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 단품", 10000, 3)
                )
        );

        OrderSaveResponse result = orderCommandService.saveOrder(request);

        assertThat(result.orderId()).isEqualTo(1L);
        assertThat(result.orderProductSaveResponseList().size()).isEqualTo(2);
    }

}
