package oblig3.salesreg.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oblig3.salesreg.Main;
import oblig3.salesreg.model.Address;
import oblig3.salesreg.model.CDAO;
import oblig3.salesreg.model.ADAO;
import oblig3.salesreg.model.Customer;

public class StatsViewController {



    private Main main;
    private Stage dialogStage;


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




    public void setMain(Main main) {
        this.main = main;

        // Add observable list data to the table
        //customerTable.setItems(main.getCustomerData());
    }









}
