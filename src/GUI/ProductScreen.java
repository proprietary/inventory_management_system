package GUI;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.text.Normalizer;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import javafx.stage.Stage;
import model.Part;
import model.Product;

public class ProductScreen {
    @FXML private Button cancelButton, saveButton, partsSearchButton, addPartButton;
    @FXML private Text title;
    @FXML private TextField idField;
    @FXML private TextField productNameField, invField, priceField, minField, maxField;
    @FXML private TableView<Part> associatedPartsTableView;
    @FXML private TableView<Part> partsSearchTableView;
    @FXML private TableColumn<Part, Integer> idColumn, inventoryColumn, partsSearchIdColumn, partsSearchInventoryColumn;
    @FXML private TableColumn<Part, String> nameColumn, partsSearchNameColumn;
    @FXML private TableColumn<Part, Double> priceColumn, partsSearchPriceColumn;
    @FXML private TextField partsSearchQueryField;
    private final ObservableList<Part> parts;
    private final ObservableList<Product> products;
    /// Index into the products list. Only applicable for Modify mode.
    private int index = -1;
    private final CreateOrUpdateMode mode;

    private ProductScreen(ObservableList<Product> products, ObservableList<Part> parts, CreateOrUpdateMode mode, int index) {
        this.products = products;
        this.parts = parts;
        this.mode = mode;
        this.index = index;
    }

    public ProductScreen(ObservableList<Product> products, int index, ObservableList<Part> parts) {
        this(products, parts, CreateOrUpdateMode.UPDATE, index);
    }

    public ProductScreen(ObservableList<Product> products, ObservableList<Part> parts) {
        this(products, parts, CreateOrUpdateMode.CREATE, -1);
    }

    @FXML private void initialize() {
        // change title text
        if (this.mode == CreateOrUpdateMode.CREATE) {
            title.setText("Add Product");
        } else if (this.mode == CreateOrUpdateMode.UPDATE) {
            title.setText("Modify Product");
            // populate fields with existing data
            Product p = this.getProductModel();
            idField.setText(Integer.toString(p.getId()));
            productNameField.setText(p.getName());
            invField.setText(Integer.toString(p.getStock()));
            priceField.setText(Double.toString(p.getPrice()));
            minField.setText(Integer.toString(p.getMin()));
            maxField.setText(Integer.toString(p.getMax()));
            associatedPartsTableView.setItems(p.getAllAssociatedParts());
        }

        // Tables

        // parts search has all the parts
        partsSearchIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partsSearchNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partsSearchInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partsSearchPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        FilteredList<Part> partResults = new FilteredList<>(this.parts);
        partsSearchTableView.setItems(partResults);
        partsSearchTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // react to search input
        Consumer<String> handleQueryChange = (String s) -> {
            partResults.setPredicate((Part p) -> p.getName().toLowerCase().contains(s.toLowerCase()));
        };
        // perform search on every keystroke
        partsSearchQueryField.textProperty().addListener((observableValue, s, t1) -> {
            handleQueryChange.accept(t1);
        });
        partsSearchButton.setOnAction(evt -> {
            handleQueryChange.accept(partsSearchQueryField.getText());
        });
        // add selected part to associated parts list
        addPartButton.setOnAction(evt -> {
            final Part selectedPart = partsSearchTableView.getSelectionModel().getSelectedItem();
            final Product thisProduct = this.getProductModel();
            if (selectedPart != null && thisProduct != null)
                thisProduct.addAssociatedPart(selectedPart);
        });
        // bind the table columns to the products data store
        idColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        initInputMasks();
    }

    @FXML private void save() {
        snapshot().ifPresent((Product p) -> {
            if (this.mode == CreateOrUpdateMode.CREATE) {
                this.products.add(p);
            } else {
                this.products.set(this.index, p);
            }
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

    private Product getProductModel() {
        if (this.mode == CreateOrUpdateMode.CREATE) {
            // TODO store a persistent object reference for the newly created Product
        } else if (this.mode == CreateOrUpdateMode.UPDATE) {
        }
        return this.products.get(this.index);
    }

    private void initInputMasks() {
        // set up input masks
        invField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
        priceField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.doubleFieldFormatter));
        minField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
        maxField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
    }
}
