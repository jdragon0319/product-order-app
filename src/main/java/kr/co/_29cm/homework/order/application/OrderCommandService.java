package kr.co._29cm.homework.order.application;

import jakarta.persistence.EntityNotFoundException;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderProduct;
import kr.co._29cm.homework.order.domain.OrderRepository;
import kr.co._29cm.homework.order.dto.OrderProductSaveResponse;
import kr.co._29cm.homework.order.dto.OrderSaveRequest;
import kr.co._29cm.homework.order.dto.OrderSaveResponse;
import kr.co._29cm.homework.order.exception.EmptyOrderProductException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static kr.co._29cm.homework.order.exception.OrderExceptionMessages.NOT_FOUND_ORDER;
import static kr.co._29cm.homework.order.exception.OrderExceptionMessages.NOT_SAVE_ORDER_BY_EMPTY_ORDER_PRODUCT;

@Service
public class OrderCommandService {

    private final OrderRepository orderRepository;

    public OrderCommandService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderSaveResponse saveOrder(OrderSaveRequest request) {
        if (request.hasNotOrderProduct()) {
            throw new EmptyOrderProductException(NOT_SAVE_ORDER_BY_EMPTY_ORDER_PRODUCT);
        }
        Order savedOrder = orderRepository.save(Order.of(request));
        return OrderSaveResponse.of(
                savedOrder.getId(),
                createOrderProductSaveResponse(savedOrder)
        );
    }

    private List<OrderProductSaveResponse> createOrderProductSaveResponse(Order savedOrder) {
        List<OrderProduct> orderProductList = savedOrder.getOrderProductList();
        return orderProductList.stream()
                .map(orderProduct -> OrderProductSaveResponse.of(
                        orderProduct.getProductName(), orderProduct.getPrice(), orderProduct.getQuantity())
                )
                .toList();
    }

}
