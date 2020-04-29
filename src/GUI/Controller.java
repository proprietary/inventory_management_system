package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Part;
import model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class Controller {
    @FXML
    private Button addPartButton, modifyPartButton, addProductButton, modifyProductButton, deletePartButton, deleteProductButton;

    // products table
    private final ArrayList<Product> products;
    private ObservableList<Product> products_;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TextField productsQueryField;
    @FXML
    private Button productsSearchButton;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    // parts table
    private final ArrayList<Part> parts;
    private ObservableList<Part> parts_;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TextField partsQueryField;
    @FXML
    private Button partsSearchButton;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    public Controller(ArrayList<Part> parts, ArrayList<Product> products) {
        this.parts = parts;
        this.parts_ = FXCollections.observableArrayList(parts);
        this.products_ = FXCollections.observableArrayList(products);
        this.products = products;
    }

    @FXML
    public void initialize() {
        // initialize products table
        // name the columns
        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        // configure search results
        FilteredList<Product> productSearchResults = new FilteredList<>(this.products_);
        productsTableView.setItems(productSearchResults);
        productsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // filter search results to query
        EventHandler<ActionEvent> handleProductSearch = actionEvent -> {
            productSearchResults.setPredicate((Product p) -> p.getName().toLowerCase().contains(productsQueryField.getText().toLowerCase()));
        };
        productsQueryField.setOnAction(handleProductSearch);
        productsSearchButton.setOnAction(handleProductSearch);


        // initialize parts table
        // name the columns
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        // configure search results
        FilteredList<Part> partSearchResults = new FilteredList<>(this.parts_);
        partsTableView.setItems(partSearchResults);
        partsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // filter search results to query
        EventHandler<ActionEvent> handlePartSearch = actionEvent -> {
            partSearchResults.setPredicate((Part p) -> p.getName().toLowerCase().contains(partsQueryField.getText().toLowerCase()));
        };
        partsQueryField.setOnAction(handlePartSearch);
        partsSearchButton.setOnAction(handlePartSearch);
    }

    private void launchProductWindow(ProductScreen screenController) {
        try {
            Stage s = new Stage();
            s.initModality(APPLICATION_MODAL);
            s.setTitle("Add/Modify Product");
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("ProductScreen.fxml"));
            ldr.setController(screenController);
            Parent root = ldr.load();
            s.setScene(new Scene(root));
            s.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void launchPartWindow(PartScreen screenController) {
        try {
            Stage v = new Stage();
            v.initModality(APPLICATION_MODAL);
            v.setTitle("Add/Modify Part");
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PartScreen.fxml"));
            ldr.setController(screenController);
            Parent root = ldr.load();
            v.setScene(new Scene(root));
            v.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @FXML
    private void deletePart() {
        // J.  Write code to implement exception controls with custom error messages for one requirement out of each of the following sets (pick one from each):
        // - including a confirm dialogue for all “Delete” and “Cancel” buttons
        Optional<ButtonType> confirmation = Alert.confirm("This will delete the selected part.");
        confirmation.ifPresent(a -> {
            if (a == ButtonType.OK) {
                getSelectedPart().ifPresent((Part p) -> {
                    this.parts_.remove(p);
                });
            }
        });
    }

    @FXML
    private void deleteProduct() {
        // J.  Write code to implement exception controls with custom error messages for one requirement out of each of the following sets (pick one from each):
        // - including a confirm dialogue for all “Delete” and “Cancel” buttons
        Optional<ButtonType> confirmation = Alert.confirm("This will delete the selected product");
        confirmation.ifPresent(a -> {
            if (a == ButtonType.OK) {
                getSelectedProduct().ifPresent((Product p) -> {
                    this.products_.remove(p);
                });
            }
        });
    }

    @FXML
    private void modifyProduct() {
        getSelectedProduct().ifPresent((Product p) -> {
            int idx = this.products_.indexOf(p);
            if (idx != -1)
                launchProductWindow(new ModifyProductScreen(this.products_, this.parts_, idx));
        });
    }

    @FXML
    private void addProduct() {
        launchProductWindow(new AddProductScreen(this.products_, this.parts_));
    }

    @FXML
    private void modifyPart() {
        getSelectedPart().ifPresent((Part p) -> {
            int idx = this.parts_.indexOf(p);
            if (idx != -1)
                launchPartWindow(new ModifyPartScreen(this.parts_, idx));
        });
    }

    @FXML
    private void addPart() {
        launchPartWindow(new AddPartScreen(this.parts_, new InHouse("", 0.0, 0, 0,0, 0)));;
    }

    @FXML
    private void exitProgram() {
        System.exit(1);
    }

    private Optional<Part> getSelectedPart() {
        final TableView.TableViewSelectionModel<Part> selectionModel = this.partsTableView.getSelectionModel();
        final Part selectedPart = selectionModel.getSelectedItem();
        return Optional.ofNullable(selectedPart);
    }

    private Optional<Product> getSelectedProduct() {
        final TableView.TableViewSelectionModel<Product> selectionModel = this.productsTableView.getSelectionModel();
        final Product selectedProduct = selectionModel.getSelectedItem();
        return Optional.ofNullable(selectedProduct);
    }
}
