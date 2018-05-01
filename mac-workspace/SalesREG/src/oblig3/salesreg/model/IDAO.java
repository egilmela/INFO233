package oblig3.salesreg.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oblig3.salesreg.Main;

import java.sql.*;
import java.util.ArrayList;

public class IDAO {




    /**
     * Returns a invoice owned by a specific customer
     * @param invoiceID some invoiceID
     * @return an invoice object
     */
    public Invoice getInvoiceByID(int invoiceID) throws SQLException {
        Invoice invoice;
        try (
                Connection conn = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM invoice WHERE invoice_id = ?");
                PreparedStatement productStmt = conn.prepareStatement("SELECT product FROM invoice_items WHERE invoice = ?")
        ) {
            stmt.setInt(1, invoiceID);
            try (ResultSet result = stmt.executeQuery()) {

                //add result data to invoice object
                invoice = new Invoice(invoiceID, result.getInt("customer"), result.getString("dato"));
                //add all the product ids to the invoice
                productStmt.setInt(1, invoiceID);
                ResultSet productResult = productStmt.executeQuery();
                while (productResult.next()) {
                    invoice.addProduct(productResult.getInt("product"));
                }
            }
        }
        return invoice;
    }


    /**
     * Returns a list of all invoices owned by a specific customer
     * @param customerID a customerID
     * @return an ObservableList of Invoices
     */
    public static ObservableList getInvoiceByCustomerID(int customerID) throws SQLException {

        ObservableList<Invoice> invoices = FXCollections.observableArrayList();

        try (
                Connection c = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement stmt = c.prepareStatement("SELECT * FROM invoice WHERE customer = ?");
                PreparedStatement productStmt = c.prepareStatement("SELECT product FROM invoice_items WHERE invoice_items.invoice = ?");
        ) {
            stmt.setInt(1, customerID);

            System.out.println("customerID: " + customerID);


            try (ResultSet result = stmt.executeQuery()) {

                while (result.next()) {
                    Invoice invoice = new Invoice(result.getInt("invoice_id"), result.getInt("customer"), result.getString("dato"));
                    productStmt.setInt(1, invoice.getInvoiceID());
                    ResultSet productResult = productStmt.executeQuery();

                    System.out.println("invoice.getInvoiceID(): " + invoice.getInvoiceID());

                    while (productResult.next()) {
                        invoice.addProduct(productResult.getInt("product"));
                    }
                    invoices.add(invoice);
                }
            }
        }
        return invoices;
    }


    /**
     * Returns a list of all invoices
     */
    public ArrayList<Invoice> getAllInvoices() throws SQLException {
        ArrayList<Invoice> invoices = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM invoice");
                PreparedStatement productStmt = conn.prepareStatement("SELECT product FROM invoice_items WHERE invoice_items.invoice = ?");
                ResultSet result = stmt.executeQuery()
        ) {
            while (result.next()) {
                Invoice invoice = new Invoice(result.getInt("invoice_id"), result.getInt("customer"), result.getString("dato"));
                productStmt.setInt(1, invoice.getInvoiceID());
                ResultSet productResult = productStmt.executeQuery();
                while (productResult.next()) {
                    invoice.addProduct(productResult.getInt("product"));
                }
                invoices.add(invoice);
            }
        }
        return invoices;
    }

    public void createInvoice(Invoice invoice) throws SQLException {
        try (Connection conn = DriverManager.getConnection(Main.getDBPath());
             PreparedStatement invoiceStmt = conn.prepareStatement("INSERT INTO Invoice(customer, dato) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
             PreparedStatement productStmt = conn.prepareStatement("INSERT INTO invoice_items(invoice, product) VALUES (?, ?)")
        ) {
            invoiceStmt.setInt(1, invoice.getCustomerID());
            invoiceStmt.setString(2, invoice.getDate());
            invoiceStmt.executeUpdate();

            //get the key for the invoice row that was created
            ResultSet result = invoiceStmt.getGeneratedKeys();
            productStmt.setInt(1, result.getInt(1));

            //insert all products for the invoice
            for (Integer productID : invoice.getProductList()) {
                productStmt.setInt(2, productID);
                productStmt.executeUpdate();
            }
        }
    }

    public void updateInvoice(Invoice invoice) throws SQLException {
        try (Connection conn = DriverManager.getConnection(Main.getDBPath());
             PreparedStatement invoiceStmt = conn.prepareStatement("UPDATE invoice SET customer = ?, dato = ? WHERE invoice_id = ?");
             PreparedStatement deleteProductsStmt = conn.prepareStatement("DELETE FROM invoice_items WHERE invoice = ?");
             PreparedStatement productStmt = conn.prepareStatement("INSERT INTO invoice_items(invoice, product) VALUES (?, ?)")
        ) {
            invoiceStmt.setInt(1, invoice.getCustomerID());
            invoiceStmt.setString(2, invoice.getDate());
            invoiceStmt.setInt(3, invoice.getInvoiceID());
            invoiceStmt.executeUpdate();

            deleteProductsStmt.setInt(1, invoice.getInvoiceID());
            deleteProductsStmt.executeUpdate();

            productStmt.setInt(1, invoice.getInvoiceID());
            for (Integer productID : invoice.getProductList()) {
                productStmt.setInt(2, productID);
                productStmt.executeUpdate();
            }
        }
    }
}
