package GUI;

import javafx.application.Application;
import javafx.collections.ObservableList;
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

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader ldr = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        ldr.setController(new Controller(Main.generateTestParts(), Main.generateTestProducts()));
        Parent root = ldr.load();
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ArrayList<Part> generateTestParts() {
        ArrayList<Part> parts = new ArrayList<>();
        parts.add(new InHouse(1, "in house part #1", 10.0, 20, 10, 100, 0));
        parts.add(new InHouse(2, "in house part #2", 10.0, 20, 10, 100, 0));
        parts.add(new Outsourced(3, "outsourced part #1", 10.0, 20, 10, 100, "company #1"));
        parts.add(new InHouse(4, "in house part #3", 10.0, 20, 10, 100, 0));
        return parts;
    }

    public static ArrayList<Product> generateTestProducts() {
        return new ArrayList<Product>() {{
            add(new Product(1, "Product #1", 10.0, 20, 10, 100));
            add(new Product(2, "Product #2", 10.0, 20, 10, 100));
            add(new Product(3, "Product #3", 10.0, 20, 10, 100));
        }};
    }
}
