package pl.mentor.banking.analyzer.payment;

import pl.mentor.banking.analyzer.service.PaymentMethod;

public class PaymentTerminal {

    private final PaymentMethod method;

    public PaymentTerminal(PaymentMethod method){
        System.out.println("PaymentMethod constructor..." + method.getClass().getName());
        this.method = method;
    }

    public void performPayment(double amount){
        var res = method.pay(amount);
        System.out.println("Płatność: " + res);
    }
}
