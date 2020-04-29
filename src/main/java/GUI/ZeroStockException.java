package GUI;

import model.Part;

public class ZeroStockException extends Exception {
    public ZeroStockException(final Part p) {
        super("Stock count must be at least 1");
    }
}
