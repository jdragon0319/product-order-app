package kr.co._29cm.homework.product.infra;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.Repository;

@Primary
public interface JpaProductRepository extends ProductRepository, Repository<Product, Long> {

}
