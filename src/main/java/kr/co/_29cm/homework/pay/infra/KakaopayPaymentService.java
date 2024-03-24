package kr.co._29cm.homework.pay.infra;

import kr.co._29cm.homework.pay.application.PaymentService;
import kr.co._29cm.homework.view.output.OutputView;
import org.springframework.stereotype.Service;

@Service
public class KakaopayPaymentService implements PaymentService {

    @Override
    public boolean payment(Long orderId, Integer totalPrice) {
        OutputView.printPayment();
        return true;
    }
}
