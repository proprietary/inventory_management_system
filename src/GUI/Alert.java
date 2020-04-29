package GUI;

import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.function.Consumer;

public class Alert {
    public static void display(String message, Consumer<? super ButtonType> callback) {
        javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, message);
        a.showAndWait().ifPresent(callback);
    }

    public static void display(String message) {
        Alert.display(message, b -> {
        });
    }

    public static Optional<ButtonType> confirm(String message) {
        javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmation");
        a.setHeaderText("Are you sure?");
        a.setContentText(message);
        return a.showAndWait();
    }
}
