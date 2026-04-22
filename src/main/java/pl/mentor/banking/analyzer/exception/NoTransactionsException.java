package pl.mentor.banking.analyzer.exception;

public class NoTransactionsException extends RuntimeException {
    public NoTransactionsException(String s) {
        super("Brak transakcji");
    }
}
