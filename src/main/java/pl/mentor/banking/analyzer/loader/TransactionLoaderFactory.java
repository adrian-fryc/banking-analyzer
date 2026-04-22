package pl.mentor.banking.analyzer.loader;

public class TransactionLoaderFactory {
    public static TransactionSource getLoader(String path){
        if(path.endsWith(".csv")){
            System.out.println("TransactionLoaderFactory: Rozpoczynam wczytywanie danych z CSV");
            return new CsvTransactionLoader();
        } else if (path.endsWith(".json")) {
            System.out.println("TransactionLoaderFactory: Rozpoczynam wczytywanie danych z JSON");
            return new JsonTransactionLoader();
        }else{
            throw new IllegalArgumentException("Unsupported file type");
        }
    }
}
