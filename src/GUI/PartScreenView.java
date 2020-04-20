package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import static javafx.stage.Modality.APPLICATION_MODAL;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PartScreenView extends Stage {
    public PartScreenView () throws IOException {
        initModality(APPLICATION_MODAL);
        setTitle("Add/Modify Part");
        FXMLLoader ldr = new FXMLLoader(getClass().getResource("AddPartScreen.fxml"));
        ldr.setController(new AddPartScreen());
        Parent root = ldr.load();
        setScene(new Scene(root));
    }
}
