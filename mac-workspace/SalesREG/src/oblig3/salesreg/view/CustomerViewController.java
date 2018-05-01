package oblig3.salesreg.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import oblig3.salesreg.Main;
import oblig3.salesreg.model.*;
import oblig3.salesreg.view.CustomerViewController;
import java.sql.SQLException;
import java.util.Date;


public class CustomerViewController {

    private CDAO customerDAO = new CDAO();
    private ADAO addressDAO = new ADAO();
    private IDAO invoiceDAO = new IDAO();
    private PDAO productDAO = new PDAO();


    private Customer customer;
    private Address address;

    Date lastClickTime;
    Customer tempC;
    Invoice tempI;
    public static boolean handleNewCustomerButtonPressed = false;
    public static Number fromTableInvoiceID;

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, Number> customerIDColumn;

    @FXML
    private TableView<Invoice> customerOverviewInvoiceTable;
    @FXML
    private TableColumn<Invoice, Number> invoiceIDColumn;
    @FXML
    private TableColumn<Invoice, Number> noProductsColumn;
    @FXML
    private TableColumn<Invoice, Number> totalColumn;

    @FXML
    private Label customerNameLabel;
    @FXML
    private Label customerIDLabel;
    @FXML
    private Label telephoneLabel;
    @FXML
    private Label billingAccountLabel;
    @FXML
    private Label streetNumberLabel;
    @FXML
    private Label streetNameLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label postalTownLabel;

    // Reference to the main application.
    private Main main;









    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteCustomer() {
        int selectedIndex = customerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {

           // customerTable.getItems().set(selectedIndex, new Customer());

            try {
                customerDAO.deleteCustomerWithID(new Customer(selectedIndex+1, null, 0, null, null));
                customerTable.getItems().remove(selectedIndex);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            main.alert();
        }
    }


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CustomerViewController() {

    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        // Initialize the customer table with the two columns.
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        customerIDColumn.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty());

        // Listen for selection changes and show the customer details when changed.
        customerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCustomerDetails(newValue));


        // Initialize the invoice table with columns.
        invoiceIDColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceIDProperty());
        //noProductsColumn.setCellValueFactory(cellData -> cellData.getValue().getProductListSize());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty());


        // Listen for selection changes and show the customer invoice details when changed.
       // customerOverviewInvoiceTable.getSelectionModel().selectedItemProperty().addListener(
        //       (observable, oldValue, newValue) -> showCustomerDetails(newValue));



    }


    public void setMain(Main main) {
        this.main = main;

        // Add observable list data to the table
        customerTable.setItems(main.getCustomerData());
    }






        private void showCustomerDetails(Customer customer) {
        try {

            if (customer != null && customer.getAddressID() != 0) {
                String streetNameNo;
                String postNoTown;

                address = addressDAO.getAddress(customer.getAddressID());

                customerIDLabel.setText(String.valueOf(customer.getCustomerID()));
                customerNameLabel.setText(customer.getCustomerName());
                telephoneLabel.setText(customer.getTelephone());
                billingAccountLabel.setText(customer.getBillingAccount());

                streetNameNo = address.getStreetName() + " " + address.getStreetNumber();
                streetNameLabel.setText(streetNameNo);

                postNoTown = address.getPostalCode() + " " + address.getPostalTown().toUpperCase();
                postalCodeLabel.setText(postNoTown);

                customerOverviewInvoiceTable.setItems(invoiceDAO.getInvoiceByCustomerID(customer.getCustomerID()));



/*

                ObservableList<Integer> productList;
                //productList = addProductsToObservableList(invoice);

                int s = productList.size();
                double sum = 0;
                while (s != 0) {
                    Product product = null;
                    try {
                        System.out.println("s-1: " + (s-1));
                        product = productDAO.getProduct(productList.get(s-1));


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    sum += product.getPrice();
                    s--;
                }
*/


            }
            else {
                customerNameLabel.setText("");
                customerIDLabel.setText("");
                telephoneLabel.setText("");
                billingAccountLabel.setText("");
                streetNameLabel.setText("");
                postalCodeLabel.setText("");


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleCustomerRowSelect() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) return;
        if(selectedCustomer != tempC) {
            tempC = selectedCustomer;
            lastClickTime = new Date();
        }
        else if(selectedCustomer == tempC) {

            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();

            if (diff < 300) { //another click registered in 300 millis
                    main.showCustomerEditDialog(selectedCustomer);
                    showCustomerDetails(selectedCustomer);
                    main.getCustomerData().add(selectedCustomer);
            }
            else {
                lastClickTime = new Date();
            }
        }
    }


    @FXML
    private void handleInvoiceRowSelect() {
        Invoice selectedInvoice = customerOverviewInvoiceTable.getSelectionModel().getSelectedItem();

        if (selectedInvoice == null) return;
        if(selectedInvoice != tempI){
            tempI = selectedInvoice;
            lastClickTime = new Date();
        }
        else if(selectedInvoice == tempI) {

            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();

            if (diff < 300){ //another click registered in 300 millis


                //getInvoiceByCustomerID()


                //customer.getCustomerID();

                //
                //Customer tempCustomer = new Customer(int customerID, String customerName, int addressID, String telephone, String billingAccount);

                //Customer tempCustomer = customerTable.getSelectionModel().getSelectedItem();




                fromTableInvoiceID = invoiceIDColumn.getCellData(selectedInvoice);
                //System.out.println("fromTableInvoiceID " + fromTableInvoiceID);

                main.showInvoiceView(customerTable.getSelectionModel().getSelectedItem());



            } else {
                lastClickTime = new Date();
            }
        }
    }


    @FXML
    private void handleNewCustomer() {
        Customer tempCustomer = new Customer();

        handleNewCustomerButtonPressed = true;
        boolean okClicked = main.showCustomerEditDialog(tempCustomer);

        if (okClicked) {
            main.getCustomerData().add(tempCustomer);
            customerTable.setItems(main.getCustomerData());
        }
    }


    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected customer.
     */
    @FXML
    private void handleEditCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();


        if (selectedCustomer != null) {
            boolean okClicked = main.showCustomerEditDialog(selectedCustomer);

            if (okClicked) {
                showCustomerDetails(selectedCustomer);

                //customer.
                main.getCustomerData().add(selectedCustomer);
                customerTable.setItems(main.getCustomerData());
            }
        }
        else {
            main.alert();
        }
    }

    /**
     * Called when the user presses escape.
     */
    @FXML
    private void handleEscape(KeyEvent key) {
        if (KeyCode.ESCAPE == key.getCode()) {
            main.getPrimaryStage().close();
        }
    }

}