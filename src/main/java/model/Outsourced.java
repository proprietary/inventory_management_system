package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Outsourced extends Part {
    private StringProperty companyNameProperty = new SimpleStringProperty();

    public Outsourced(String name, double price, int stock, int min, int max, String companyName) {
        super(name, price, stock, min, max);
        this.companyNameProperty.set(companyName);
    }

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyNameProperty.set(companyName);
    }

    public String getCompanyName() {
        return companyNameProperty.get();
    }

    public void setCompanyName(String companyName) {
        this.companyNameProperty.set(companyName);
    }

    public StringProperty companyNameProperty() {
        return this.companyNameProperty;
    }
}
