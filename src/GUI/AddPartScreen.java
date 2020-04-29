package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Part;

public class AddPartScreen extends PartScreen {
    private Part part;

    public AddPartScreen(ObservableList<Part> allParts, Part newPart) {
        super(allParts);
        this.part = newPart;
    }

    @FXML public void initialize() {
        super.initialize();
        // select In House as the default mode
        inHouseRadioButton.setSelected(true);
    }

    @Override
    protected Part getPartModel() {
        return part;
    }

    @Override protected void setPartModel(Part part) {
        this.part = part;
    }

    @FXML @Override protected void save() {
        final Part p = getPartModel();
        try {
            checkInventory();
            if (p != null)
                allParts.add(p);
        } catch (InventoryBoundsException e) {
            Alert.display(e.getMessage());
        }
    }
}
