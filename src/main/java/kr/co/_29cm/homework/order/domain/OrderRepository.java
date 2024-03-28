package kr.co._29cm.homework.order.domain;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends Repository<Order, Long> {
    Order save(Order order);

    @Query(value = "select o from Order o join fetch o.orderProducts where o.id = :id")
    Optional<Order> findById(@Param("id") Long id);

}
