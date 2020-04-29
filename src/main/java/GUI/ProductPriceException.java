package GUI;

public class ProductPriceException extends Exception {
    public ProductPriceException() {
        super("Price of all parts is more than the price of the product");
    }
}
