package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Part;

public class ModifyPartScreen extends PartScreen {
    private int index;
    private Part part;

    public ModifyPartScreen(ObservableList<Part> allParts, int index) {
        super(allParts);
        this.index = index;
        this.part = allParts.get(index);
    }

    @Override
    public Part getPartModel() {
        return this.part;
    }

    @Override
    protected void setPartModel(Part part) {
        this.part = part;
    }

    private Part getUnderlyingPartModel() {
        return allParts.get(index);
    }

    private void setUnderlyingPartModel(Part part) {
        allParts.set(index, part);
    }

    @FXML
    public void initialize() {
        super.initialize();
        inHouseRadioButton.setSelected(isInHouse());
        outsourcedRadioButton.setSelected(isOutsourced());
    }

    /**
     * Validate the temporary Part object, then commit it to the underlying data model
     */
    @FXML
    @Override
    protected void save() {
        try {
            final Part p = getPartModel();
            checkInventory();
            // sanity check
            if (p != null && Part.isValid(p)) {
                setUnderlyingPartModel(p);
                closeModalImpl();
            }
        } catch (InventoryBoundsException | ZeroStockException | MinMaxMismatchException e) {
            Alert.display(e.getMessage());
        }

    }
}
