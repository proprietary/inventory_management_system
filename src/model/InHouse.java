package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InHouse extends Part {
    private IntegerProperty machineIdProperty = new SimpleIntegerProperty();

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineIdProperty.set(machineId);
    }

    public InHouse(String name, double price, int stock, int min, int max, int machineId) {
        super(name, price, stock, min, max);
        this.machineIdProperty.set(machineId);
    }

    public int getMachineId() {
        return this.machineIdProperty.get();
    }

    public void setMachineId(int machineId) {
        this.machineIdProperty.set(machineId);
    }

    public IntegerProperty machineIdProperty() {
        return this.machineIdProperty;
    }
}
