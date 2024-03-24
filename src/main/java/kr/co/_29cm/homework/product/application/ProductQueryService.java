package kr.co._29cm.homework.product.application;

import jakarta.persistence.EntityNotFoundException;
import kr.co._29cm.homework.order.dto.OrderProductRequest;
import kr.co._29cm.homework.order.dto.OrderProductResponse;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co._29cm.homework.product.exception.ProductExceptionMessages.NOT_FOUND_PRODUCT;

@Service
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrderProductResponse getOrderProductInfo(OrderProductRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_PRODUCT.formatted(request.productId())));
        product.checkStock(request.quantity());
        return OrderProductResponse.of(product.getId(), product.getName(), product.getPrice(), request.quantity());
    }
}
