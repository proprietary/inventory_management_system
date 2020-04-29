package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import model.Product;

import java.util.ArrayList;

public class Main extends Application {
    private static ArrayList<Part> testParts = new ArrayList<Part>() {{
        add(new InHouse("in house part #1", 10.0, 20, 10, 100, 0));
        add(new InHouse("in house part #2", 10.0, 20, 10, 100, 0));
        add(new Outsourced("outsourced part #1", 10.0, 20, 10, 100, "company #1"));
        add(new InHouse("in house part #3", 10.0, 20, 10, 100, 0));
    }};

    private static ArrayList<Product> testProducts = new ArrayList<Product>() {{
        add(new Product(1, "Product #1", 10.0, 20, 10, 100));
        add(new Product(2, "Product #2", 10.0, 20, 10, 100));
        add(new Product(3, "Product #3", 10.0, 20, 10, 100));
    }};

    public static void main(String[] args) {
        launch(args);
    }

    public static ArrayList<Part> generateTestParts() {
        return testParts;
    }

    public static ArrayList<Product> generateTestProducts() {
        return testProducts;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader ldr = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        ldr.setController(new Controller(Main.generateTestParts(), Main.generateTestProducts()));
        Parent root = ldr.load();
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 1500, 800));
        primaryStage.show();
    }
}
