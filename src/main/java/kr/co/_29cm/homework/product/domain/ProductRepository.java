package kr.co._29cm.homework.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    <S extends Product> List<S> saveAll(Iterable<S> entities);

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void deleteAll();
}
