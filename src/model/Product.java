package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product implements IndexedById {
    /// Auto-incremented ID counter
    private static int IdSequence = 0;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private StringProperty nameProperty = new SimpleStringProperty();
    private DoubleProperty priceProperty = new SimpleDoubleProperty();
    private IntegerProperty stockProperty = new SimpleIntegerProperty();
    private IntegerProperty minProperty = new SimpleIntegerProperty();
    private IntegerProperty maxProperty = new SimpleIntegerProperty();

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        if (IdSequence < id) {
            // update auto-increment counter to highest value
            IdSequence = ++id;
        }
        this.nameProperty.set(name);
        this.priceProperty.set(price);
        this.stockProperty.set(stock);
        this.minProperty.set(min);
        this.maxProperty.set(max);
    }

    public Product(String name, double price, int stock, int min, int max) {
        this(GenerateId(), name, price, stock, min, max);
    }

    public static int GenerateId() {
        return ++IdSequence;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nameProperty.get();
    }

    public void setName(String name) {
        nameProperty.set(name);
    }

    public void setStock(int stock) {
        stockProperty.set(stock);
    }

    public void setMin(int min) {
        minProperty.set(min);
    }

    public void setMax(int max) {
        maxProperty.set(max);
    }

    public void setPrice(double price) {
        priceProperty.set(price);
    }

    public int getId() {
        return id;
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

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public IntegerProperty stockProperty() {
        return stockProperty;
    }

    public DoubleProperty priceProperty() {
        return priceProperty;
    }

    public IntegerProperty minProperty() {
        return minProperty;
    }

    public IntegerProperty maxProperty() {
        return maxProperty;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public void addAssociatedPart(Part... parts) {
        for (final Part p : parts)
            associatedParts.add(p);
    }

    public boolean deleteAssociatedPart(Part selectedPart) {
        return associatedParts.remove(selectedPart);
    }
}
