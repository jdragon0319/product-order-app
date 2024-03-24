package kr.co._29cm.homework.order.exception;

public class EmptyOrderProductException extends RuntimeException {
    public EmptyOrderProductException(String message) {
        super(message);
    }
}
