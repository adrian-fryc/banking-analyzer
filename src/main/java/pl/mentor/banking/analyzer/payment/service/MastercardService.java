package pl.mentor.banking.analyzer.payment.service;

import pl.mentor.banking.analyzer.payment.BaseCardPayment;

public class MastercardService extends BaseCardPayment {

    public MastercardService(){
        System.out.println("Mastercard constructor...");
    }

//    @Override
//    public Double pay(double amount) {
//        System.out.println("MasterCard payment...");
//        logTransaction(amount);
//        return amount;
//    }

    @Override
    protected Double executeSpecificPayment(double amount) {
        System.out.println("MasterCard payment...");
        return amount;
    }
}
