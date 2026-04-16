package pl.mentor.banking.analyzer.service;

public class VisaService implements PaymentMethod{

    public VisaService(){
        System.out.println("Visa constructor...");
    }

    @Override
    public Double pay(double amount) {
        System.out.println("Visa payment...");
        return amount;
    }
}
