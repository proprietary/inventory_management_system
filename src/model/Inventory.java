package model;

import javafx.collections.ObservableList;

import java.util.Optional;

public class Inventory {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    private static Optional<IndexedById> lookup(Iterable<? extends IndexedById> objects, int id) {
        for (final IndexedById x : objects) {
            if (x.getId() == id) {
                return Optional.of(x);
            }
        }
        return Optional.empty();
    }

    public void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public Part lookupPart(int partId) {
        return (Part) Inventory.lookup(allParts, partId).orElse(null);
    }

    public Product lookupProduct(int productId) {
        return (Product) Inventory.lookup(allProducts, productId).orElse(null);
    }

    public void updatePart(int index, Part newPart) {
        allParts.set(index, newPart);
    }

    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

