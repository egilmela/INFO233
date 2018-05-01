package oblig3.salesreg.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Customer.
 */
public class Customer extends Invoice {

    private final IntegerProperty customerID;
    private final StringProperty customerName;
    private final IntegerProperty addressID;
    private final StringProperty telephone;
    private final StringProperty billingAccount;

    /**
     * Default constructor.
     */
    public Customer() {
        this(0, null, 0, null, null);
    }

    /**
     * Constructor
     */
    public Customer(int customerID, String customerName, int addressID, String telephone, String billingAccount) {


        this.customerID = new SimpleIntegerProperty(customerID);
        this.customerName = new SimpleStringProperty(customerName);
        this.addressID = new SimpleIntegerProperty(addressID);
        this.telephone = new SimpleStringProperty(telephone);
        this.billingAccount = new SimpleStringProperty(billingAccount);


        //this.customerID = customerID;
        //this.customerName = customerName;
        //this.addressID = addressID;
        //this.telephone = phoneNumber;
        //this.billingAccount = billingAccount;



    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public StringProperty customerNameProperty() {
        return customerName;
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

    public Integer getAddressID() {
        return addressID.get();
    }

    public void setAddressID(Integer addressID) {
        this.addressID.set(addressID);
    }

    public IntegerProperty addressIDProperty() {
        return addressID;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

    public String getBillingAccount() {
        return billingAccount.get();
    }

    public void setBillingAccount(String billingAccount) {
        this.billingAccount.set(billingAccount);
    }

    public StringProperty billingAccountProperty() {
        return billingAccount;
    }
}