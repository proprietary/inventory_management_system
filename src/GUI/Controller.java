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
import model.Part;
import model.Product;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class Controller {
    @FXML private Button exitButton;
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;

    // products table
    private final ArrayList<Product> products;
    private ObservableList<Product> products_;
    @FXML private TableView<Product> productsTableView;
    @FXML private TextField productsQueryField;
    @FXML private Button productsSearchButton;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;

    // parts table
    private final ArrayList<Part> parts;
    private ObservableList<Part> parts_;
    @FXML private TableView<Part> partsTableView;
    @FXML private TextField partsQueryField;
    @FXML private Button partsSearchButton;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;

    public Controller(ArrayList<Part> parts, ArrayList<Product> products) {
        this.parts = parts;
        this.parts_ = FXCollections.observableArrayList(parts);
        this.products_ = FXCollections.observableArrayList(products);
        this.products = products;
    }

    @FXML public void initialize() {
        // initialize products table
        // name the columns
        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        // configure search results
        FilteredList<Product> productSearchResults = new FilteredList<>(FXCollections.observableArrayList(products));
        productsTableView.setItems(productSearchResults);
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
        FilteredList<Part> partSearchResults = new FilteredList<>(FXCollections.observableArrayList(parts));
        partsTableView.setItems(partSearchResults);
        // filter search results to query
        EventHandler<ActionEvent> handlePartSearch = actionEvent -> {
            partSearchResults.setPredicate((Part p) -> p.getName().toLowerCase().contains(partsQueryField.getText().toLowerCase()));
        };
        partsQueryField.setOnAction(handlePartSearch);
        partsSearchButton.setOnAction(handlePartSearch);

        // Exit program button
        exitButton.setOnAction(event -> {
            System.exit(0);
        });

        // Show Add Part window on clicking the button
        addPartButton.setOnAction(evt -> {
            try {
                Stage v = new Stage();
                v.initModality(APPLICATION_MODAL);
                v.setTitle("Add/Modify Part");
                FXMLLoader ldr = new FXMLLoader(getClass().getResource("PartScreen.fxml"));
                ldr.setController(new PartScreen(this.parts_, CreateOrUpdateMode.CREATE));
                Parent root = ldr.load();
                v.setScene(new Scene(root));
                v.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                System.exit(1);
            }
        });

        modifyPartButton.setOnAction(evt -> {
            try {
                Stage v = new Stage();
                v.initModality(APPLICATION_MODAL);
                v.setTitle("Modify Part");
                FXMLLoader ldr = new FXMLLoader(getClass().getResource("PartScreen.fxml"));
                ldr.setController(new PartScreen(this.parts_, CreateOrUpdateMode.UPDATE, 0));
                Parent root = ldr.load();
                v.setScene(new Scene(root));
                v.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                System.exit(1);
            }
        });
    }
}
