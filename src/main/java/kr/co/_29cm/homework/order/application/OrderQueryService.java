package kr.co._29cm.homework.order.application;

import jakarta.persistence.EntityNotFoundException;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co._29cm.homework.order.exception.OrderExceptionMessages.NOT_FOUND_ORDER;

@Service
public class OrderQueryService {
    private final OrderRepository orderRepository;

    public OrderQueryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow((() -> new EntityNotFoundException(NOT_FOUND_ORDER.formatted(id))));
    }
}
