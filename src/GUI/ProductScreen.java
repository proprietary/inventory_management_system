package GUI;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import model.Part;
import model.Product;

import java.util.Optional;
import java.util.function.Consumer;

abstract public class ProductScreen {
    protected final ObservableList<Part> parts;
    protected final ObservableList<Product> products;
    @FXML
    protected Button cancelButton, saveButton, partsSearchButton, addPartButton;
    @FXML
    protected Text title;
    @FXML
    protected TextField idField;
    @FXML
    protected TextField productNameField, invField, priceField, minField, maxField;
    @FXML
    protected TableView<Part> associatedPartsTableView;
    @FXML
    protected TableView<Part> partsSearchTableView;
    @FXML
    protected TableColumn<Part, Integer> idColumn, inventoryColumn, partsSearchIdColumn, partsSearchInventoryColumn;
    @FXML
    protected TableColumn<Part, String> nameColumn, partsSearchNameColumn;
    @FXML
    protected TableColumn<Part, Double> priceColumn, partsSearchPriceColumn;
    @FXML
    protected TextField partsSearchQueryField;

    protected ProductScreen(ObservableList<Product> products, ObservableList<Part> parts) {
        this.products = products;
        this.parts = parts;
    }

    protected abstract Product getProductModel();

    @FXML
    protected void initialize() {
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

    protected void checkProductCount() throws ProductPartCountException {
        // J.  Write code to implement exception controls with custom error messages for one requirement out of each of the following sets (pick one from each):
        // - ensuring that a product must always have at least one part
        final Product p = getProductModel();
        if (p.getAllAssociatedParts().size() < 1) {
            throw new ProductPartCountException("There must be at least one part associated with the product");
        }
    }

    protected void checkProductPrice() throws ProductPriceException {
        // J.  Write code to implement exception controls with custom error messages for one requirement out of each of the following sets (pick one from each):
        //  ensuring that the price of a product cannot be less than the cost of the parts
        final Product p = getProductModel();
        double sum = 0.0;
        for (final Part x : p.getAllAssociatedParts()) {
            sum += x.getPrice();
        }
        if (sum > p.getPrice()) {
            throw new ProductPriceException(String.format("Price of all parts is $%.2f which is more than the price of the product: $%.2f", sum, p.getPrice()));
        }
    }

    @FXML
    private void closeModal() {
        // J.  Write code to implement exception controls with custom error messages for one requirement out of each of the following sets (pick one from each):
        // - including a confirm dialogue for all “Delete” and “Cancel” buttons
        Optional<ButtonType> confirmation = Alert.confirm("This will cancel your current operation");
        confirmation.ifPresent(a -> {
            if (a == ButtonType.OK) {
                Stage s = (Stage) cancelButton.getScene().getWindow();
                s.close();
            }
        });
    }

    @FXML
    private void addAssociatedPart() {
        final Part selectedPart = partsSearchTableView.getSelectionModel().getSelectedItem();
        final Product thisProduct = this.getProductModel();
        if (selectedPart != null && thisProduct != null)
            thisProduct.addAssociatedPart(selectedPart);
    }

    @FXML
    private void deleteAssociatedPart() {
        // J.  Write code to implement exception controls with custom error messages for one requirement out of each of the following sets (pick one from each):
        // - including a confirm dialogue for all “Delete” and “Cancel” buttons
        final Optional<ButtonType> confirmation = Alert.confirm("This will remove the selected part from being associated with this product.");
        confirmation.ifPresent(a -> {
            if (a == ButtonType.OK) {
                getSelectedAssociatedPart().ifPresent((final Part p) -> {
                    getProductModel().deleteAssociatedPart(p);
                });
            }
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
