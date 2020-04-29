package GUI;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Part;

public class ModifyPartScreen extends PartScreen {
    private int index;

    public ModifyPartScreen(ObservableList<Part> allParts, int index) {
        super(allParts);
        this.index = index;
    }

    @Override public Part getPartModel() {
        return allParts.get(index);
    }

    @Override protected void setPartModel(Part part) {
        allParts.set(index, part);
    }

    @FXML public void initialize() {
        super.initialize();
        inHouseRadioButton.setSelected(isInHouse());
        outsourcedRadioButton.setSelected(isOutsourced());
    }

    @FXML @Override protected void save() {
        // dummy function because the data binding already saved everything
    }
}
