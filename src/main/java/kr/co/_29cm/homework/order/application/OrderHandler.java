package kr.co._29cm.homework.order.application;

import jakarta.persistence.EntityNotFoundException;
import kr.co._29cm.homework.order.dto.OrderProductRequest;
import kr.co._29cm.homework.order.dto.OrderProductResponse;
import kr.co._29cm.homework.order.dto.OrderSaveRequest;
import kr.co._29cm.homework.order.dto.OrderSaveResponse;
import kr.co._29cm.homework.order.exception.EmptyOrderProductException;
import kr.co._29cm.homework.order.exception.InputOrderException;
import kr.co._29cm.homework.product.application.ProductQueryService;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.exception.SoldOutException;
import kr.co._29cm.homework.view.input.InputView;
import kr.co._29cm.homework.view.output.OutputView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static kr.co._29cm.homework.order.exception.OrderExceptionMessages.NOT_SAVE_ORDER_BY_EMPTY_ORDER_PRODUCT;

@Component
public class OrderHandler {

    private final ProductQueryService productQueryService;
    private final OrderCommandService orderCommandService;

    public OrderHandler(ProductQueryService productQueryService, OrderCommandService orderCommandService) {
        this.productQueryService = productQueryService;
        this.orderCommandService = orderCommandService;
    }

    public OrderSaveResponse createOrder() {
        printProductList();
        List<OrderProductResponse> orderProducts = new ArrayList<>();
        while(true) {
            try {
                OrderProductRequest req = createOrderProductRequest();
                if (req.isEndOrder()) {
                    if (orderProducts.isEmpty()) {
                        throw new EmptyOrderProductException(NOT_SAVE_ORDER_BY_EMPTY_ORDER_PRODUCT);
                    }
                    break;
                }
                OrderProductResponse response = productQueryService.getOrderProductInfo(req);
                orderProducts.add(response);
            } catch (SoldOutException | EntityNotFoundException | EmptyOrderProductException e) {
                OutputView.printException(e.getMessage());
            } catch (InputOrderException | NumberFormatException e) {
                OutputView.retryMessage();
                OutputView.printException(e.getMessage());
            }
        }

        return orderCommandService.saveOrder(OrderSaveRequest.of(orderProducts));
    }

    private void printProductList() {
        List<Product> allProductList = productQueryService.findAll();
        OutputView.printProducts(allProductList);
    }

    private OrderProductRequest createOrderProductRequest() {
        String productId = inputProductId();
        String quantity = inputQuantity();
        if (" ".equals(productId) && " ".equals(quantity)) {
            return OrderProductRequest.endRequest();
        }
        return OrderProductRequest.of(Long.valueOf(productId), Integer.parseInt(quantity));
    }

    private String inputQuantity() {
        OutputView.printQuantity();
        return InputView.input();
    }

    private String inputProductId() {
        OutputView.printProductId();
        return InputView.input();
    }
}
