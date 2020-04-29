package GUI;

public class ZeroStockException extends Exception {
    public ZeroStockException() {
        super("Stock count must be at least 1");
    }
}
