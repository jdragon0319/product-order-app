package kr.co._29cm.homework.order.application;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderProduct;
import kr.co._29cm.homework.order.domain.OrderProducts;
import kr.co._29cm.homework.order.domain.OrderRepository;
import kr.co._29cm.homework.pay.application.PaymentService;
import kr.co._29cm.homework.pay.exception.FailedPayException;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.product.domain.Stock;
import kr.co._29cm.homework.product.domain.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OrderProcessHandlerTest {
    @Autowired
    @InjectMocks
    private OrderProcessHandler orderProcessHandler;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StockRepository stockRepository;

    @MockBean
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productRepository.save(Product.of(3L, "29CM입사기념선물", 30000, 100));
    }

    @DisplayName("주문성공")
    @Test
    void orderSuccess() throws InterruptedException {
        BDDMockito.given(paymentService.payment(any(),any())).willReturn(true);
        List<OrderProduct> orderProductList = List.of(
                OrderProduct.of(3L, "29CM입사기념선물", 30000, 5)
        );
        Order savedOrder = orderRepository.save(Order.of(new OrderProducts(orderProductList)));

        orderProcessHandler.order(savedOrder.getId());

        Stock stock = stockRepository.findById(3L).get();
        assertThat(stock.getQuantity()).isEqualTo(95);
    }

    @DisplayName("결제 실패하면 재고가 롤백되어야 한다.")
    @Test
    void failedPay() throws InterruptedException {
        BDDMockito.given(paymentService.payment(any(),any())).willReturn(false);
        List<OrderProduct> orderProductList = List.of(
                OrderProduct.of(3L, "29CM입사기념선물", 30000, 5)
        );
        Order savedOrder = orderRepository.save(Order.of(new OrderProducts(orderProductList)));

        try {
            orderProcessHandler.order(savedOrder.getId());
        } catch (FailedPayException e) {
            System.out.println("fail");
        }

        Stock stock = stockRepository.findById(3L).get();
        assertThat(stock.getQuantity()).isEqualTo(100);
    }

}
