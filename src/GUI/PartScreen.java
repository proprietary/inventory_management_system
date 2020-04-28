package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;

import java.util.Optional;

public class PartScreen {
    private int index_;
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

    private PartScreen(ObservableList<Part> allParts, CreateOrUpdateMode mode, int index) {
        this.allParts = allParts;
        this.mode = mode;
        this.index_ = index;
    }

    public PartScreen(ObservableList<Part> allParts, int index) {
        this(allParts, CreateOrUpdateMode.UPDATE, index);
    }

    public PartScreen(ObservableList<Part> allParts) {
        this(allParts, CreateOrUpdateMode.CREATE, -1);
    }

    @FXML public void initialize() {
        // Radio button logic

        // ensure that only one of either In House or Outsourced can be checked
        ToggleGroup tg = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(tg);
        outsourcedRadioButton.setToggleGroup(tg);

        // show different fields based on which radio is toggled
        inHouseRadioButton.setOnAction(evt -> { updateBasedOnPartType(); });
        outsourcedRadioButton.setOnAction(evt -> { updateBasedOnPartType(); });

        // For modifying an existing part: Populate fields
        if (mode == CreateOrUpdateMode.UPDATE) {
            Part existingPart = this.allParts.get(this.index_);
            idField.setText(Integer.toString(existingPart.getId()));
            nameField.setText(existingPart.getName());
            inventoryField.setText(Integer.toString(existingPart.getStock()));
            priceField.setText(Double.toString(existingPart.getPrice()));
            minField.setText(Integer.toString(existingPart.getMin()));
            maxField.setText(Integer.toString(existingPart.getMax()));
            if (existingPart instanceof Outsourced) {
                outsourcedRadioButton.setSelected(true);
                updateBasedOnPartType();
                companyNameField.setText(((Outsourced) existingPart).getCompanyName());
            } else if (existingPart instanceof InHouse) {
                inHouseRadioButton.setSelected(true);
                updateBasedOnPartType();
                machineIdField.setText(Integer.toString(((InHouse) existingPart).getMachineId()));
            }
        } else if (mode == CreateOrUpdateMode.CREATE) {
            // select In House as the default mode
            inHouseRadioButton.setSelected(true);
            updateBasedOnPartType();
        }

        // set up input masks--prevent incorrectly formatted input
        inventoryField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
        priceField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.doubleFieldFormatter));
        minField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
        maxField.setTextFormatter(new TextFormatter<String>(FormattedTextFields.integerFieldFormatter));
    }

    private void updateBasedOnPartType() {
        // show either Machine ID field or Company name field
        machineIdField.setDisable(isOutsourced());
        companyNameField.setDisable(isInHouse());
    }

    @FXML private void save() {
        // TODO
        // TODO detect if already exists
        final Optional<Part> p = snapshot();
        p.ifPresent(part -> {
            System.out.println(p.get().getName() + " saved!");
            allParts.add(part);
        });
    }

    /**
     * Capture snapshot of the form state as a Part object
     * @return Optional of the Part type; if form state is invalid, an empty Optional
     */
    private Optional<Part> snapshot() {
        String name = nameField.getText();
        int stock = Integer.parseInt(inventoryField.getText());
        double price = Double.parseDouble(priceField.getText());
        int min = Integer.parseInt(minField.getText());
        int max = Integer.parseInt(maxField.getText());
        if (max < min) {
            // Display error message
            Alert.display("The minimum number of parts should not exceed the maximum number of parts.");
            return Optional.empty();
        }
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

    @FXML private void closeModal() {
        Stage s = (Stage) cancelButton.getScene().getWindow();
        s.close();
    }
}
