package oblig3.salesreg.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import oblig3.salesreg.model.Address;
import oblig3.salesreg.model.CDAO;
import oblig3.salesreg.model.ADAO;
import oblig3.salesreg.model.Customer;

import java.sql.SQLException;
/**
 * Edit details of a customer.
 *
 */
public class CustomerEditDialogController {

    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerIDField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField billingAccountField;
    @FXML
    private TextField streetNumberField;
    @FXML
    private TextField streetNameField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField postalTownField;


    private Stage dialogStage;
    //private Customer customer;
    private Address address;
    private boolean okClicked = false;

    private CDAO customerDAO = new CDAO();
    private ADAO addressDAO = new ADAO();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    /**
     * Sets the customer to be edited in the dialog.
     *
     * @param customer
     */
    public void setCustomer(Customer customer) {

        try {
            if (customer != null && customer.getAddressID() != 0) {
                address = addressDAO.getAddress(customer.getAddressID());

                customerNameField.setText(customer.getCustomerName());
                //customerIDField.setText(String.valueOf(customer.getCustomerID()));
                telephoneField.setText(customer.getTelephone());
                billingAccountField.setText(customer.getBillingAccount());
                streetNumberField.setText(address.getStreetNumber());
                streetNameField.setText(address.getStreetName());
                postalCodeField.setText(address.getPostalCode());
                postalTownField.setText(address.getPostalTown());
            }
            else {
                customerNameField.setText("");
                telephoneField.setText("");
                billingAccountField.setText("");
                streetNumberField.setText("");
                streetNameField.setText("");
                postalCodeField.setText("");
                postalTownField.setText("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {

            Customer customer = new Customer(0, customerNameField.getText(), 0, telephoneField.getText(), billingAccountField.getText());
            Address address = new Address(0, streetNumberField.getText(), streetNameField.getText(), postalCodeField.getText(), postalTownField.getText());

            try {
                int addressID = addressDAO.getAddressByID(address);

                if (addressID == 0) {
                    addressDAO.createAddress(address);
                    customer.setAddressID(addressDAO.getAddressByID(address));
                    customerDAO.setAddress(customer);
                    customerDAO.createCustomer(customer);
                }
                else {
                    customer.setAddressID(addressID);
                    customerDAO.createCustomer(customer);
                }
            } catch (SQLException ex) {
                ex.getMessage();
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Called when the user presses escape.
     */
    @FXML
    private void handleEscape(KeyEvent key) {
        if (KeyCode.ESCAPE == key.getCode()) {
            dialogStage.close();
        }
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (customerNameField.getText() == null || customerNameField.getText().length() == 0) {
            errorMessage += "Not a valid name!\n";
        }
        if (streetNameField.getText() == null || streetNameField.getText().length() == 0) {
            errorMessage += "Not a valid street name!\n";
        }
        if (streetNumberField.getText() == null || streetNumberField.getText().length() == 0) {
            errorMessage += "Not a valid street number!\n";
        }
        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "Not a valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
                if (!(postalCodeField.getText().length() == 4)) {
                    errorMessage += "Must be exactly 4 numbers!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Not a valid postal code (numbers only)!\n";
            }

        }
        if (postalTownField.getText() == null || postalTownField.getText().length() == 0) {
            errorMessage += "No valid postal town!\n";
        }
        if (telephoneField.getText() == null || telephoneField.getText().length() == 0) {
            errorMessage += "Not a valid number!\n";
        }
        if (billingAccountField.getText() == null || billingAccountField.getText().length() == 0) {
            errorMessage += "Not a valid account number!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}