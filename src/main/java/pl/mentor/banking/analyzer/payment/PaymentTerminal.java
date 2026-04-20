package pl.mentor.banking.analyzer.payment;

public class PaymentTerminal {

    private final PaymentMethod method;

    public PaymentTerminal(PaymentMethod method){
        System.out.println("PaymentTerminal constructor..." + method.getClass().getName());
        this.method = method;
    }

    public void performPayment(double amount){
        System.out.println("PaymentTerminal performPayment start: Zaraz rozpocznie się płatność przy pomocy: " + method.getClass().getName());
        var res = method.pay(amount);
        System.out.println("PaymentTerminal performPayment end : Płatność: " + res);
    }
}
