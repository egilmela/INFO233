package oblig3.salesreg.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import oblig3.salesreg.Main;
import oblig3.salesreg.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import static oblig3.salesreg.view.CustomerViewController.fromTableInvoiceID;


public class InvoiceViewController extends Customer{

    private Main main;
    private Stage dialogStage;
    private boolean saveClicked = false;

    private CDAO customerDAO = new CDAO();
    private ADAO addressDAO = new ADAO();
    private PDAO productDAO = new PDAO();
    private IDAO invoiceDAO = new IDAO();


    private Address address;


    private final IntegerProperty productID;
    private final StringProperty productName;
    private final StringProperty productDescription;
    private final IntegerProperty price;
    private final IntegerProperty category;

    private final IntegerProperty tax;
    private final IntegerProperty discount;
    private final IntegerProperty net;

    /**
     * Default constructor
     */
    public InvoiceViewController() { this(0, null, null, 0, 0, 0, 0, 0); }

    /**
     * constructor
     */
    public InvoiceViewController(int productID, String productName, String productDescription, int category, int price, int tax, int discount, int net) {
        this.productID = new SimpleIntegerProperty(productID);
        this.productName = new SimpleStringProperty(productName);
        this.productDescription = new SimpleStringProperty(productDescription);
        this.category = new SimpleIntegerProperty(category);
        this.price = new SimpleIntegerProperty(price);
        this.tax = new SimpleIntegerProperty(tax);
        this.discount = new SimpleIntegerProperty(discount);
        this.net = new SimpleIntegerProperty(net);
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

    public Integer getTax() {
        return tax.get();
    }

    public void setTax(Integer tax) { this.tax.set(tax); }

    public IntegerProperty taxProperty() {
        return tax;
    }

    public Integer getDiscount() {
        return discount.get();
    }

    public void setDiscount(Integer discount) {
        this.discount.set(discount);
    }

    public IntegerProperty discountProperty() {
        return discount;
    }

    public Integer getNet() {
        return net.get();
    }

    public void setNet(Integer net) { this.net.set(net); }

    public IntegerProperty netProperty() {
        return net;
    }



    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    private TableView<InvoiceViewController> invoiceTable;
    @FXML
    private TableColumn<InvoiceViewController, Number> productIDColumn;
    @FXML
    private TableColumn<InvoiceViewController, String> nameColumn;
    @FXML
    private TableColumn<InvoiceViewController, String> descriptionColumn;
    @FXML
    private TableColumn<InvoiceViewController, Number> categoryColumn;
    @FXML
    private TableColumn<InvoiceViewController, Number> amountColumn;
    @FXML
    private TableColumn<InvoiceViewController, Number> itemPriceColumn;
    @FXML
    private TableColumn<InvoiceViewController, Number> taxColumn;
    @FXML
    private TableColumn<InvoiceViewController, Number> discountColumn;
    @FXML
    private TableColumn<InvoiceViewController, Number> netColumn;


    @FXML
    private Label customerNameLabel;
    @FXML
    private Label streetNameLabel;
    @FXML
    private Label postalCodeLabel;


    @FXML
    private Label netLabel;
    @FXML
    private Label taxableLabel;
    @FXML
    private Label VATLabel;
    @FXML
    private Label discountLabel;
    @FXML
    private Label sumTotalLabel;

    @FXML
    private DatePicker issueDate;
    @FXML
    private DatePicker dueDate;

    @FXML
    private TextField commentsField;
    @FXML
    private TextField invoiceIDField;
    @FXML
    private TextField KIDField;


    @FXML
    private ComboBox addProductIDComboBox;
    @FXML
    private ComboBox addProductNameComboBox;



    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {



        // Initialize the table with columns.
        productIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().productDescriptionProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        itemPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        taxColumn.setCellValueFactory(cellData -> cellData.getValue().taxProperty());
        discountColumn.setCellValueFactory(cellData -> cellData.getValue().discountProperty());
        netColumn.setCellValueFactory(cellData -> cellData.getValue().netProperty());

        // Listen for selection changes and show the invoice details when changed.
        //invoiceTable.getSelectionModel().selectedItemProperty().addListener(
         //       (observable, oldValue, newValue) -> showInvoiceDetails(newValue));

        // Listen for selection changes and show the invoice details when changed.
        invoiceTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTable(0));


/*
        productIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Invoice, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Invoice, String> data) {
                return data.getValue() // the Invoice
                        .getProductList();
            }
        });
        */
    }


    /**
     * Returns
     * @return
     */
    public ObservableList<Integer> addProductsToObservableList(Invoice invoice) {

        //ArrayList<Integer> invoiceList = new ArrayList<>();
        ObservableList<Integer> products = FXCollections.observableArrayList();

        //Invoice i = invoiceDAO.getInvoiceByID(invoice.getInvoiceID());
        //System.out.println("addProducts: " + allProducts.addAll(IDAO.getInvoiceByCustomerID(customerID)));
        //invoiceList.addAll(invoice.getProductList());
        products.addAll(invoice.getProductList());
        //System.out.println("getProductListSize: " + invoice.getProductList().size());
        return products;
    }





    public void showTable(Integer customer) {

        Invoice invoice = null;
        try {
            invoice = invoiceDAO.getInvoiceByID(fromTableInvoiceID.intValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<InvoiceViewController> tableData = FXCollections.observableArrayList();
        ObservableList<Integer> productList = addProductsToObservableList(invoice);


        int s = productList.size();
        double sum = 0;
        while (s != 0) {
            //int productID, String productName, String productDescription, int category, int amount, int price, int tax, int discount, int net) {
            Product product = null;
            try {
                System.out.println("s-1: " + (s-1));
                product = productDAO.getProduct(productList.get(s-1));


            } catch (SQLException e) {
                e.printStackTrace();
            }



            tableData.add(new InvoiceViewController(product.getProductID(), product.getProductName(), product.getProductDescription(), product.getCategory(), product.getPrice(), this.tax.getValue(), this.discount.getValue(), this.net.getValue()));




            sum += product.getPrice();
            s--;
        }



        invoiceTable.setItems(tableData);


        netLabel.setText(String.valueOf(getNet()));
        taxableLabel.setText(String.valueOf(getTax() * sum));
        VATLabel.setText(String.valueOf(getTax()));
        sumTotalLabel.setText(String.valueOf(sum));
    }






        public void showInvoiceDetails(Customer customer) {
        String streetNameNo;
        String postNoTown;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.mm.yyyy");

        try {

            Invoice invoice = invoiceDAO.getInvoiceByID(fromTableInvoiceID.intValue());



            System.out.println("fromTableInvoiceID.intValue() " + fromTableInvoiceID.intValue());
            //System.out.println("customer.getCustomerID() " + customer.getCustomerID());



            if (issueDate.getValue() != null) {
                String formattedValue = (issueDate.getValue()).format(formatter);
                issueDate.setValue(LocalDate.parse(formattedValue,formatter));
                dueDate.setValue(LocalDate.parse(formattedValue+14,formatter));
            }

            customerNameLabel.setText(customer.getCustomerName());
            address = addressDAO.getAddress(customer.getAddressID());

            streetNameNo = address.getStreetName() + " " + address.getStreetNumber();
            streetNameLabel.setText(streetNameNo);

            postNoTown = address.getPostalCode() + " " + address.getPostalTown().toUpperCase();
            postalCodeLabel.setText(postNoTown);

            invoiceIDField.setText(String.valueOf(invoice.getInvoiceID()));
            KIDField.setText("1234567");
            commentsField.setText("");




        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isSaveClicked() {
        return saveClicked;
    }





    /**
     * Called when the user clicks save.
     */
    @FXML
    private void handleSave() {


        saveClicked = true;
        dialogStage.close();
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
     * Called when the user clicks add.
     */
    @FXML
    private void handleAdd() {



    }

    /**
     * Called when the user types in productIDField
     */
    @FXML
    private void handleProductIDComboBox() {
        try {

            System.out.println("addProductIDComboBox: " + Integer.getInteger(addProductIDComboBox.getEditor().getText()));

            if (addProductIDComboBox.getEditor().getText().matches("\\d")) {


                if (productDAO.getAllProducts() != null) {

                    ObservableList<Product> productList = FXCollections.observableArrayList(productDAO.getAllProducts());

                    addProductIDComboBox.getItems().addAll(productList);
                }
            }
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

    /**
     * Called when the user types in productNameField
     */
    @FXML
    private void handleProductNameComboBox() {



        addProductNameComboBox.getEditor().getText();



    }


    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(Main main) {
        this.main = main;

        // Add observable list data to the table
        //customerTable.setItems(main.getCustomerData());
    }
}