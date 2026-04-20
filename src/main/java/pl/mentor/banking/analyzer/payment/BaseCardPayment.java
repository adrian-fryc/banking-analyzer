package pl.mentor.banking.analyzer.payment;

public abstract class BaseCardPayment implements PaymentMethod {
    // Tutaj możesz napisać zwykłą metodę z kodem!
    protected void logTransaction(double amount) {
        System.out.println("LOG: Inicjalizacja płatności kartą na kwotę: " + amount);
    }

    @Override
    public final Double pay(double amount) {
        System.out.println("BaseCardPayment pay start");
        logTransaction(amount); // 1. Klasa bazowa wymusza logowanie
        System.out.println("BaseCardPayment pay end");
        return executeSpecificPayment(amount); // 2. Wywołuje "tajną" metodę, którą napiszą dzieci
    }

    // To jest nowa "umowa" tylko dla dzieci kartowych:
    protected abstract Double executeSpecificPayment(double amount);
}
