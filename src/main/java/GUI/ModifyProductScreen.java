package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Part;
import model.Product;

public class ModifyProductScreen extends ProductScreen {
    private int index = -1;
    private Product product;

    public ModifyProductScreen(ObservableList<Product> products, ObservableList<Part> parts, int index) {
        super(products, parts);
        this.index = index;
        this.product = products.get(index);
    }

    @FXML
    protected void initialize() {
        super.initialize();
        title.setText("Modify Product");
    }

    /**
     * Validate the temporary Product object, then commit it to underlying store
     */
    @Override
    @FXML
    public void save() {
        try {
            final Product p = getProductModel();
            checkInventory();
            checkProductPrice();
            // sanity check
            if (p != null && Product.isValid(p)) {
                setUnderlyingProductModel(p);
                closeModalImpl();
            }
        } catch (ProductPriceException | ProductPartCountException | ZeroStockException | InventoryBoundsException e) {
            Alert.display(e.getMessage());
        }
    }

    @Override
    public Product getProductModel() {
        return product;
    }

    @Override
    protected void setProductModel(final Product product) {
        this.product = product;
    }

    private void setUnderlyingProductModel(final Product product) {
        this.products.set(this.index, product);
    }
}
