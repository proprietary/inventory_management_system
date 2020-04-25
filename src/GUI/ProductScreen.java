package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.text.Normalizer;
import java.util.*;
import java.util.function.UnaryOperator;

import javafx.stage.Stage;
import model.Product;

public class ProductScreen {
    @FXML private Button cancelButton, saveButton;
    @FXML private Text title;
    @FXML private TextField idField;
    @FXML private TextField productNameField, invField, priceField, minField, maxField;
    private ObservableList<Product> products;
    /// Index into the products list. Only applicable for Modify mode.
    private int index = -1;
    private CreateOrUpdateMode mode;

    private ProductScreen(ObservableList<Product> products, CreateOrUpdateMode mode, int index) {
        this.products = products;
        this.mode = mode;
        this.index = index;
    }

    public ProductScreen(ObservableList<Product> products, int index) {
        this(products, CreateOrUpdateMode.UPDATE, index);
    }

    public ProductScreen(ObservableList<Product> products) {
        this(products, CreateOrUpdateMode.CREATE, -1);
    }

    @FXML private void initialize() {
        if (this.mode == CreateOrUpdateMode.CREATE) {
            title.setText("Add Product");
        } else if (this.mode == CreateOrUpdateMode.UPDATE) {
            title.setText("Modify Product");
            // populate fields with existing data
            Product p = this.products.get(this.index);
            idField.setText(Integer.toString(p.getId()));
            productNameField.setText(p.getName());
            invField.setText(Integer.toString(p.getStock()));
            priceField.setText(Double.toString(p.getPrice()));
            minField.setText(Integer.toString(p.getMin()));
            maxField.setText(Integer.toString(p.getMax()));
        }
        // input masks
        invField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
        priceField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.doubleFieldFormatter));
        minField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
        maxField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
    }

    @FXML private void save() {
        snapshot().ifPresent((Product p) -> {
            this.products.add(p);
        });
    }

    @FXML private void closeModal() {
        Stage s = (Stage) cancelButton.getScene().getWindow();
        s.close();
    }

    /**
     * Get the current state of the form as a Java object
     * @return Product object if valid; empty if form is invalid
     */
    private Optional<Product> snapshot() {
        try {
            Product p = new Product(getName(), getPrice(), getInv(), getMin(), getMax());
            return Optional.of(p);
        } catch (NumberFormatException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private String getName() {
        return productNameField.getText();
    }

    private double getPrice() {
        return Double.parseDouble(priceField.getText());
    }

    private int getInv() {
        return Integer.parseInt(invField.getText());
    }

    private int getMin() {
        return Integer.parseInt(minField.getText());

    }

    private int getMax() {
        return Integer.parseInt(maxField.getText());
    }
}
