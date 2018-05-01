package oblig3.salesreg.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Address {

    private IntegerProperty addressID;
    private StringProperty streetNumber;
    private StringProperty streetName;
    private StringProperty postalCode;
    private StringProperty postalTown;

    /**
     * constructor.
     */
    public Address() {
        this(0, null, null, null, null);
    }


    public Address(int addressID, String streetNumber, String streetName, String postalCode, String postalTown) {

        this.addressID = new SimpleIntegerProperty(addressID);
        this.streetNumber = new SimpleStringProperty(streetNumber);
        this.streetName = new SimpleStringProperty(streetName);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.postalTown = new SimpleStringProperty(postalTown);

    }

    public int getAddressID() {
        return addressID.get();
    }

    public void setAddressID(int addressID) {
        this.addressID.set(addressID);
    }

    public IntegerProperty addressIDProperty() {
        return addressID;
    }

    public String getStreetNumber() {
        return streetNumber.get();
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber.set(streetNumber);
    }

    public StringProperty streetNumberProperty() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName.get();
    }

    public void setStreetName(String streetName) {
        this.streetName.set(streetName);
    }

    public StringProperty streetNameProperty() {
        return streetName;
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public String getPostalTown() {
        return postalTown.get();
    }

    public void setPostalTown(String postalTown) {
        this.postalTown.set(postalTown);
    }

    public StringProperty postalTownProperty() {
        return postalTown;
    }
}