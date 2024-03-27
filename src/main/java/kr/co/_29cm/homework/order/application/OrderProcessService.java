package kr.co._29cm.homework.order.application;


import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderProduct;
import kr.co._29cm.homework.order.domain.OrderProducts;
import kr.co._29cm.homework.order.domain.OrderStatus;
import kr.co._29cm.homework.pay.application.PaymentService;
import kr.co._29cm.homework.pay.exception.FailedPayException;
import kr.co._29cm.homework.product.application.StockServiceHandler;
import kr.co._29cm.homework.product.dto.StockRequest;
import kr.co._29cm.homework.product.dto.StockRequests;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co._29cm.homework.pay.exception.PayExceptionMessages.FAIL_PAY_EXCEPTION;


@Service
public class OrderProcessService {

    private final OrderQueryService orderQueryService;
    private final StockServiceHandler stockServiceHandler;
    private final PaymentService paymentService;

    public OrderProcessService(
            OrderQueryService orderQueryService,
            StockServiceHandler stockServiceHandler,
            PaymentService paymentService) {
        this.orderQueryService = orderQueryService;
        this.stockServiceHandler = stockServiceHandler;
        this.paymentService = paymentService;
    }

    @Transactional
    public void order(Long orderId) throws InterruptedException {
        Order order = orderQueryService.findById(orderId);
        StockRequests requests = StockRequests.of(createDeductStockRequestList(order.getOrderProducts()));
        stockServiceHandler.deductStocks(requests);
        boolean paymentResult = paymentService.payment(orderId, order.getTotalPrice());
        if (!paymentResult) {
            rollbackStocks(requests, orderId);
        }
        order.updateStatus(OrderStatus.COMPLETE);
    }

    private List<StockRequest> createDeductStockRequestList(OrderProducts orderProducts) {
        List<OrderProduct> orderProductList = orderProducts.getOrderProductList();
        return orderProductList.stream()
                .map(orderProduct -> StockRequest.of(
                        orderProduct.getProductId(), orderProduct.getQuantity())
                )
                .toList();
    }

    private void rollbackStocks(StockRequests requests, Long orderId) throws InterruptedException {
        stockServiceHandler.plusStocks(requests);
        throw new FailedPayException(FAIL_PAY_EXCEPTION.formatted(orderId));
    }
}
