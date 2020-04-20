package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.InHouse;
import model.Outsourced;
import model.Part;

import java.util.Optional;

public class AddPartScreen {
    private ToggleGroup productType;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField inventoryField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private TextField machineIdField;
    @FXML private TextField companyNameField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private ObservableList<Part> allParts;
    private CreateOrUpdateMode mode;

    public AddPartScreen(ObservableList<Part> allParts, CreateOrUpdateMode mode) {
        this.allParts = allParts;
        this.mode = mode;
    }

    @FXML public void initialize() {
        // Radio button logic

        // ensure that only one of either In House or Outsourced can be checked
        inHouseRadioButton.setToggleGroup(productType);
        outsourcedRadioButton.setToggleGroup(productType);

        // show different fields based on which radio is toggled
        inHouseRadioButton.setOnAction(evt -> { updateBasedOnPartType(); });
        outsourcedRadioButton.setOnAction(evt -> { updateBasedOnPartType(); });
    }

    private void updateBasedOnPartType() {
        // show either Machine ID field or Company name field
        machineIdField.setVisible(isInHouse());
        companyNameField.setVisible(isOutsourced());
    }

    @FXML private void save() {
        // TODO
        // TODO detect if already exists
        final Optional<Part> p = createNewPart();
        if (!p.isPresent()) {
            return;
        }
        System.out.println(p.get().getName() + " saved!");
    }

    private Optional<Part> createNewPart() {
        String name = nameField.getText();
        int stock = Integer.parseInt(inventoryField.getText());
        double price = Double.parseDouble(priceField.getText());
        int min = Integer.parseInt(minField.getText());
        int max = Integer.parseInt(maxField.getText());
        if (isInHouse()) {
            int machineId = Integer.parseInt(machineIdField.getText());
            InHouse newPart = new InHouse(10 /* TODO refactor to static autoincrementor */, name, price, stock, min, max, machineId);
            return Optional.of(newPart);
        } else if (isOutsourced()) {
            String companyName = companyNameField.getText();
            Outsourced newPart = new Outsourced(10 /* TODO: refactor to static autoincrementor */, name, price, stock, min, max, companyName);
            return Optional.of(newPart);
        }
        return Optional.empty();
    }

    private boolean isInHouse() {
        return inHouseRadioButton.isSelected();
    }

    private boolean isOutsourced() {
        return outsourcedRadioButton.isSelected();
    }
}
