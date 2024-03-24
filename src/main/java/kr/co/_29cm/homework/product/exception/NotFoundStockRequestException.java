package kr.co._29cm.homework.product.exception;

public class NotFoundStockRequestException extends RuntimeException {
    public NotFoundStockRequestException(String message) {
        super(message);
    }
}
