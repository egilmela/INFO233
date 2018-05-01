package oblig3.salesreg.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Invoice {
    private IntegerProperty invoiceID;
    private IntegerProperty customerID;
    private StringProperty date;
    private IntegerProperty listSize;

    private ArrayList<Integer> productList;

    /**
     * Default constructor
     */
    public Invoice() {
        this(0,0, null);
    }

    /**
     * constructor
     */
    public Invoice(int invoiceID, int customerID, String date) {

        this.invoiceID = new SimpleIntegerProperty(invoiceID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.date = new SimpleStringProperty(date);
        productList = new ArrayList<>();
    }


    public void addProduct(int productID){
        productList.add(productID);
    }

    public Integer getInvoiceID() {
        return invoiceID.get();
    }

    public void setInvoiceID(Integer invoiceID) {
        this.invoiceID.set(invoiceID);
    }

    public IntegerProperty invoiceIDProperty() {
        return invoiceID;
    }

    public Integer getCustomerID() {
        return customerID.get();
    }

    public void setCustomerID(Integer customerID) {
        this.customerID.set(customerID);
    }

    public IntegerProperty customerIDProperty() {
        return customerID;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public ArrayList<Integer> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Integer> productList) {
        this.productList = productList;
    }

    public IntegerProperty getProductListSize() {
        listSize.setValue(this.productList.size());
        return listSize;
    }
}