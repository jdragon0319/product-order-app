package kr.co._29cm.homework.product.domain;


import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends Repository<Stock, Long> {

    @Lock(value = LockModeType.OPTIMISTIC)
    @Query(value = "select s from Stock s where s.id in (:idList)")
    List<Stock> findAllByIdIn(@Param("idList") List<Long> stockIdList);

    Optional<Stock> findById(Long id);
}
