package GUI;

import javafx.beans.binding.Bindings;
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
import javafx.util.converter.NumberStringConverter;
import model.Part;
import model.Product;

abstract public class ProductScreen {
    @FXML protected Button cancelButton, saveButton, partsSearchButton, addPartButton;
    @FXML protected Text title;
    @FXML protected TextField idField;
    @FXML protected TextField productNameField, invField, priceField, minField, maxField;
    @FXML protected TableView<Part> associatedPartsTableView;
    @FXML protected TableView<Part> partsSearchTableView;
    @FXML protected TableColumn<Part, Integer> idColumn, inventoryColumn, partsSearchIdColumn, partsSearchInventoryColumn;
    @FXML protected TableColumn<Part, String> nameColumn, partsSearchNameColumn;
    @FXML protected TableColumn<Part, Double> priceColumn, partsSearchPriceColumn;
    @FXML protected TextField partsSearchQueryField;
    protected final ObservableList<Part> parts;
    protected final ObservableList<Product> products;

    protected ProductScreen(ObservableList<Product> products, ObservableList<Part> parts) {
        this.products = products;
        this.parts = parts;
    }

    protected abstract Product getProductModel();

    @FXML protected void initialize() {
        // Tables
        initPartSearchTable();
        initAssociatedPartsTable();
        // Bind text fields to data
        initBindings();
        // Prohibit nonsensical input into text fields
        initInputMasks();
    }

    private void initBindings() {
        Bindings.bindBidirectional(productNameField.textProperty(), getProductModel().nameProperty());
        Bindings.bindBidirectional(priceField.textProperty(), getProductModel().priceProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(invField.textProperty(), getProductModel().stockProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(minField.textProperty(), getProductModel().minProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(maxField.textProperty(), getProductModel().maxProperty(), new NumberStringConverter());
    }

    private void initAssociatedPartsTable() {
        // bind the table columns to the products data store
        idColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        associatedPartsTableView.setItems(getProductModel().getAllAssociatedParts());
    }

    private void initPartSearchTable() {
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
    }

    protected abstract void save();

    @FXML private void closeModal() {
        Stage s = (Stage) cancelButton.getScene().getWindow();
        s.close();
    }

    @FXML private void addAssociatedPart() {
        final Part selectedPart = partsSearchTableView.getSelectionModel().getSelectedItem();
        final Product thisProduct = this.getProductModel();
        if (selectedPart != null && thisProduct != null)
            thisProduct.addAssociatedPart(selectedPart);
    }

    @FXML private void deleteAssociatedPart() {
        getSelectedAssociatedPart().ifPresent((final Part p) -> {
            getProductModel().deleteAssociatedPart(p);
        });
    }

    private Optional<Part> getSelectedAssociatedPart() {
        return Optional.ofNullable(this.associatedPartsTableView.getSelectionModel().getSelectedItem());
    }

    private void initInputMasks() {
        // set up input masks
        invField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
        priceField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.doubleFieldFormatter));
        minField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
        maxField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
    }
}
