package GUI;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.util.converter.NumberStringConverter;
import model.Part;
import model.Product;

public class ModifyProductScreen extends ProductScreen {
    private int index = -1;
    private Product thisProduct;

    public ModifyProductScreen(ObservableList<Product> products, ObservableList<Part> parts, int index) {
        super(products, parts);
        this.index = index;
        this.thisProduct = products.get(index);
    }

    @FXML protected void initialize() {
        super.initialize();
        title.setText("Modify Product");
    }

    @Override @FXML
    public void save() {
        // dummy function because input is already saved through the data bindings
    }

    @Override
    public Product getProductModel() {
        return thisProduct;
    }
}
