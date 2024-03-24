package kr.co._29cm.homework.product.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeProductRepository implements ProductRepository {
    private final Map<Long, Product> map = new HashMap<>();

    @Override
    public <S extends Product> List<S> saveAll(Iterable<S> entities) {
        for (Product product : entities) {
            map.put(product.getId(), product);
        }
        return (List<S>) findAll();
    }

    @Override
    public List<Product> findAll() {
        return map.values().stream().toList();
    }


    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Product save(Product product) {
        map.put(product.getId(), product);
        return product;
    }

    @Override
    public void deleteAll() {
        map.clear();
    }
}
