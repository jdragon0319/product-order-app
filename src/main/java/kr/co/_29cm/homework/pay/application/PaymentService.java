package kr.co._29cm.homework.pay.application;

public interface PaymentService {
    boolean payment(Long orderId, Integer totalPrice);
}
