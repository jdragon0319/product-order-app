package kr.co._29cm.homework.order.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeOrderRepository implements OrderRepository {
    private final Map<Long, Order> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Order save(Order order) {
        order.setId(id);
        map.put(id++, order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    public void clear() {
        map.clear();
        id = 1L;
    }
}