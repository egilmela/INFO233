package oblig3.salesreg.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Product.
 */
public class Product extends Customer {

    private final IntegerProperty productID;
    private final StringProperty productName;
    private final StringProperty productDescription;
    private final IntegerProperty price;
    private final IntegerProperty category;

    /**
     * Default constructor.
     */
    public Product() {
        this(0, null, null, 0, 0);
    }

    /**
     * Constructor
     */
    public Product(int productID, String productName, String productDescription, int price, int category) {

        this.productID = new SimpleIntegerProperty(productID);
        this.productName = new SimpleStringProperty(productName);
        this.productDescription = new SimpleStringProperty(productDescription);
        this.price = new SimpleIntegerProperty(price);
        this.category = new SimpleIntegerProperty(category);
    }

    public Integer getProductID() {
        return productID.get();
    }

    public void setProductID(Integer productID) {
        this.productID.set(productID);
    }

    public IntegerProperty productIDProperty() { return productID; }

    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription.get();
    }

    public void setProductDescription(String productDescription) {
        this.productDescription.set(productDescription);
    }

    public StringProperty productDescriptionProperty() {
        return productDescription;
    }

    public Integer getPrice() {
        return price.get();
    }

    public void setPrice(Integer price) {
        this.price.set(price);
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public Integer getCategory() {
        return category.get();
    }

    public void setCategory(Integer category) {
        this.category.set(category);
    }

    public IntegerProperty categoryProperty() {
        return category;
    }
}