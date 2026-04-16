package pl.mentor.banking.analyzer.service;

public class MastercardService implements PaymentMethod{

    public MastercardService(){
        System.out.println("Mastercard constructor...");
    }

    @Override
    public Double pay(double amount) {
        System.out.println("MasterCard payment...");
        return amount;
    }
}
