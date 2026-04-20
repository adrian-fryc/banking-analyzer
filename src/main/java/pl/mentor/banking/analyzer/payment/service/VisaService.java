package pl.mentor.banking.analyzer.payment.service;

import pl.mentor.banking.analyzer.payment.BaseCardPayment;

public class VisaService extends BaseCardPayment {

    public VisaService(){
        System.out.println("Visa constructor...");
    }

//    @Override
//    public Double pay(double amount) {
//        System.out.println("Visa pay payment start...");
////        logTransaction(amount);
//        return amount;
//    }

    @Override
    protected Double executeSpecificPayment(double amount) {
        System.out.println("executeSpecificPayment Visa payment...");
        return amount;
    }
}
