package model;

import javafx.beans.property.*;

public abstract class Part implements IndexedById {
    private int id;
    /// IdSequence holds the highest auto-incrementing number
    private static int IdSequence = 0;
    private StringProperty nameProperty = new SimpleStringProperty();
    private DoubleProperty priceProperty = new SimpleDoubleProperty();
    private IntegerProperty stockProperty = new SimpleIntegerProperty();
    private IntegerProperty minProperty = new SimpleIntegerProperty();
    private IntegerProperty maxProperty = new SimpleIntegerProperty();

    private Part(int id, String name, double price, int stock, int min, int max) {
        // update auto-incremented id to highest value
        if (id > IdSequence) {
            IdSequence = id;
        }
        this.id = id;
        this.nameProperty.set(name);
        this.priceProperty.set(price);
        this.stockProperty.set(stock);
        this.minProperty.set(min);
        this.maxProperty.set(max);
    }

    protected Part(String name, double price, int stock, int min, int max) {
        this(GenerateId(), name, price, stock, min, max);
    }

    private static int GenerateId() {
        return ++IdSequence;
    }

    public void setName(String name) {
        this.nameProperty.set(name);
    }

    public void setPrice(double price) {
        this.priceProperty.set(price);
    }

    public void setStock(int stock) {
        this.stockProperty.set(stock);
    }

    public void setMin(int min) {
        this.minProperty.set(min);
    }

    public void setMax(int max) {
        this.maxProperty.set(max);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return nameProperty.get();
    }

    public double getPrice() {
        return priceProperty.get();
    }

    public int getStock() {
        return stockProperty.get();
    }

    public int getMin() {
        return minProperty.get();
    }

    public int getMax() {
        return maxProperty.get();
    }
}
