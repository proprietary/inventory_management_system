package GUI;

import javafx.scene.control.*;

import java.util.function.Consumer;

public class Alert {
    public static void display(String message, Consumer<? super ButtonType> callback) {
        javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, message);
        a.showAndWait().ifPresent(callback);
    }

    public static void display(String message) {
        Alert.display(message, b -> {});
    }
}
