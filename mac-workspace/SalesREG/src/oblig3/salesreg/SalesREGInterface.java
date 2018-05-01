package oblig3.salesreg;


import javafx.beans.property.IntegerProperty;

public interface SalesREGInterface {




    /**
     * @returns The ID of the current product
     */
    Integer getProductID();


    /**
     * @returns Assigns an ID to the current product
     *
     * @param productID ID to be assigned
     */
    void setProductID(Integer productID);


    /**
     * @returns The product ID as an IntegerProperty
     *
     *
     */
    IntegerProperty productIDProperty();



}
