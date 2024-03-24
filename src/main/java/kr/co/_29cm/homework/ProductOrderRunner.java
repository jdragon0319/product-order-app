package kr.co._29cm.homework;

import kr.co._29cm.homework.order.application.OrderHandler;
import kr.co._29cm.homework.order.application.OrderProcessHandler;
import kr.co._29cm.homework.order.dto.OrderSaveResponse;
import kr.co._29cm.homework.product.application.ProductCommandService;
import kr.co._29cm.homework.view.input.InputView;
import kr.co._29cm.homework.view.output.OutputView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
@Profile("!test")
public class ProductOrderRunner implements CommandLineRunner {

    private final ProductCommandService productCommandService;
    private final OrderHandler orderHandler;
    private final OrderProcessHandler orderProcessHandler;

    public ProductOrderRunner(
            ProductCommandService productCommandService,
            OrderHandler orderHandler,
            OrderProcessHandler orderProcessHandler
    ) {
        this.productCommandService = productCommandService;
        this.orderHandler = orderHandler;
        this.orderProcessHandler = orderProcessHandler;
    }

    @Override
    public void run(String... args) throws Exception {
        productCommandService.init(args);

        while (true) {
            OutputView.printCommand();
            String input = InputView.input();
            if (isOrderAction(input)) {
                OrderSaveResponse response = orderHandler.createOrder();
                OutputView.printOrderResult(response);
                try {
                    orderProcessHandler.order(response.orderId());
                } catch (Exception e) {
                    OutputView.printException(e.getMessage());
                }
            } else if (isEndAction(input)) {
                OutputView.printOrderEnd();
                return;
            } else {
                OutputView.printRetryCommand();
            }
        }

    }

    private static boolean isEndAction(String input) {
        return "q".equals(input) || "quit".equals(input);
    }

    private static boolean isOrderAction(String input) {
        return "o".equals(input) || "order".equals(input);
    }

}
