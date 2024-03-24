package kr.co._29cm.homework.view.output;

import kr.co._29cm.homework.order.dto.OrderSaveResponse;
import kr.co._29cm.homework.product.domain.Product;

import java.text.DecimalFormat;
import java.util.List;

public class OutputView {
    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###");

    public static void printCommand() {
        System.out.println("입력(o[order]: 주문, q[quit]: 종료 : ");
    }

    public static void printOrderResult(OrderSaveResponse response) {
        System.out.println("주문 내역:");
        OutputView.printBoundary();
        OutputView.printMessage(response.getOrderInfo());

        OutputView.printBoundary();
        OutputView.printPrice("주문금액: ", response.calculateProductPrice());
        if (response.calculateShippingCharge() > 0) {
            OutputView.printPrice("배송비: ",response.calculateShippingCharge());
        }

        OutputView.printBoundary();
        OutputView.printPrice("지불금액: ", response.calculateTotalPrice());
    }

    public static void printOrderEnd() {
        System.out.println("고객님의 주문 감사합니다.");
    }

    public static void printRetryCommand() {
        System.out.println("o[order], q[quit] 중에 입력해주세요.");
    }

    public static void printProducts(List<Product> allProductList) {
        System.out.println("상품번호  상품명                                     판매가격    재고수");
        allProductList.forEach(product -> {
            System.out.println(product.toString());
        });
    }

    public static void printException(String message) {
        System.out.println(message);
    }

    public static void printQuantity() {
        System.out.print("수량: ");
    }

    public static void printProductId() {
        System.out.print("상품번호: ");
    }

    public static void retryMessage() {
        System.out.println("상품번호, 수량을 확인하고 다시 입력해주세요.");
    }

    public static void printBoundary() {
        System.out.println("------------------------------------");
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printPrice(String message, Integer calculateTotalPrice) {
        System.out.println(message + decimalFormat.format(calculateTotalPrice) + "원");
    }

    public static void printPayment() {
        System.out.println("결제를 시도합니다.");
    }
}
