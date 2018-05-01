package oblig3.salesreg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oblig3.salesreg.model.*;
import oblig3.salesreg.view.CustomerEditDialogController;
import oblig3.salesreg.view.CustomerViewController;
import oblig3.salesreg.view.InvoiceViewController;
import oblig3.salesreg.view.StatsViewController;

import static oblig3.salesreg.view.CustomerViewController.handleNewCustomerButtonPressed;

public class Main extends Application {

    private final static String SQLPath = "resources/db/db.sql";
    private final static String DBPath = "jdbc:sqlite:resources/db/storage.db";

    private Stage primaryStage;
    private TabPane rootLayout;


    public static String getDBPath(){
        return DBPath;
    }


    /**
     * Constructor
     */
    public Main() {}


    private void s() {

        if (new File(DBPath.replace("jdbc:sqlite:", "")).isFile()) {

            System.out.println("DB present");
        }
        else {
            System.out.println("DB init...");

            try(
                    Connection conn = DriverManager.getConnection(DBPath);
                    Statement stmt = conn.createStatement();
                    Scanner sql = new Scanner(new File(SQLPath))
            ){
                sql.useDelimiter(";");

                conn.setAutoCommit(true);

                while (sql.hasNext()) {
                    String qry = sql.next();
                    if (!qry.equals("")) {
                        stmt.addBatch(qry);
                    }
                }
                stmt.executeBatch();
                conn.commit();
            }
            catch (SQLException e ) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Returns the data as an observable list of customers.
     * @return
     */
    public ObservableList<Customer> getCustomerData() {

        ArrayList<Customer> allCustomers = new ArrayList<>();
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            allCustomers.addAll(CDAO.getAllCustomers());
            customers.addAll(allCustomers);

        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }
        return customers;
    }



    @Override
    public void start(Stage primaryStage) {
        //database setup
        s();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("salesREG");

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));

        initRootLayout();
        showCustomerView();
        showStatsView();
        //showInvoiceView();
    }


    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootView.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Shows the person overview inside the root layout.
     */
    public void showCustomerView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CustomerView.fxml"));

            AnchorPane personView = loader.load();

            Tab tab = new Tab();

            tab.setText("Customers");
            tab.setClosable(false);
            tab.setContent(personView);

            rootLayout.getTabs().add(0, tab);

            // Give the controller access to the main app.
            CustomerViewController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Shows the stats overview inside the root layout.
     */
    public void showStatsView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/StatsView.fxml"));

            AnchorPane statsView = loader.load();

            Tab tab = new Tab();

            tab.setText("Stats");
            tab.setClosable(false);
            tab.setContent(statsView);

            rootLayout.getTabs().add(1, tab);

            // Give the controller access to the main app.
            StatsViewController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean showCustomerEditDialog(Customer customer) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CustomerEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            if (handleNewCustomerButtonPressed) {
                  dialogStage.setTitle("New customer");
            }

            dialogStage.setTitle("Edit customer");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the customer into the controller.
            CustomerEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCustomer(customer);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean showInvoiceView(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/InvoiceView.fxml"));
            AnchorPane invoiceView = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Invoice");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(invoiceView);
            dialogStage.setScene(scene);

            InvoiceViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMain(this);

            controller.showInvoiceDetails(customer);
            controller.showTable(customer.getCustomerID());




            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void alert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(getPrimaryStage());
        alert.setTitle("Nothing selected");
        alert.setHeaderText("Nothing selected");
        alert.setContentText("Nothing selected");
        alert.showAndWait();
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }



    public static void main(String[] args) {
        launch(args);
    }
}