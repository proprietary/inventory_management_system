package model;

import javafx.beans.property.*;

public abstract class Part implements IndexedById {
    /// IdSequence holds the highest auto-incrementing number
    private static int IdSequence = 0;
    private int id;
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty stock = new SimpleIntegerProperty();
    private IntegerProperty min = new SimpleIntegerProperty();
    private IntegerProperty max = new SimpleIntegerProperty();

    public Part(int id, String name, double price, int stock, int min, int max) {
        // update auto-incremented id to highest value
        if (id > IdSequence) {
            IdSequence = id;
        }
        this.id = id;
        this.name.set(name);
        this.price.set(price);
        this.stock.set(stock);
        this.min.set(min);
        this.max.set(max);
    }

    protected Part(String name, double price, int stock, int min, int max) {
        this(GenerateId(), name, price, stock, min, max);
    }

    private static int GenerateId() {
        return ++IdSequence;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getStock() {
        return stock.get();
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public int getMin() {
        return min.get();
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public int getMax() {
        return max.get();
    }

    public void setMax(int max) {
        this.max.set(max);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty stockProperty() {
        return stock;
    }

    public IntegerProperty minProperty() {
        return min;
    }

    public IntegerProperty maxProperty() {
        return max;
    }

    public static boolean isBlank(final Part p) {
        return p.getName().isEmpty();
    }

    public static boolean hasSaneInventoryValues(final Part p) {
        return p.getStock() >= p.getMin() && p.getStock() <= p.getMax();
    }

    public static boolean isValid(final Part p) {
        return !isBlank(p) && hasSaneInventoryValues(p);
    }
}
