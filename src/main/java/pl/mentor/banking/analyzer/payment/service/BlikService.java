package pl.mentor.banking.analyzer.payment.service;

import pl.mentor.banking.analyzer.payment.PaymentMethod;

public class BlikService implements PaymentMethod {
    @Override
    public Double pay(double amount) {
        System.out.println("BlikService pay payment...");
        return amount;
    }
}
