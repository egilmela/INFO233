package oblig3.salesreg.model;

import oblig3.salesreg.Main;
import java.sql.*;
import java.util.ArrayList;

public class CDAO {

    public Customer getCustomerByID(int customerID) throws SQLException {

        Customer customer;
        try(
                Connection c = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement prepStatement = c.prepareStatement("SELECT * FROM customer WHERE customer_id = ?")
        ){
            prepStatement.setInt(1, customerID);
            try(ResultSet result = prepStatement.executeQuery()) {

                customer = new Customer(customerID, result.getString("customer_name"), result.getInt("address"), result.getString("phone_number"), result.getString("billing_account"));
            }
        }
        return customer;
    }

    /**
     * Gets an arraylist containing all customers
     * @return list of all customers
     */
    public static ArrayList<Customer> getAllCustomers() throws SQLException {

        ArrayList<Customer> list = new ArrayList<>();
        try(
                Connection c = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement prepStatement = c.prepareStatement("SELECT * FROM customer");
                ResultSet result = prepStatement.executeQuery()
        ){
            while (result.next()) {
                list.add(new Customer(result.getInt("customer_id"), result.getString("customer_name"), result.getInt("address"), result.getString("phone_number"), result.getString("billing_account")));
            }
        }
        return list;
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        if(customer.getCustomerName().isEmpty() || customer.getTelephone().isEmpty() || customer.getBillingAccount().isEmpty()){
            return false;
        }
        try(
                Connection c = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement prepStatement = c.prepareStatement("UPDATE customer SET customer_name = ?, phone_number = ?, " +
                        "billing_account = ? WHERE customer_id = ?")
        ){

            prepStatement.setString(1, customer.getCustomerName());
            prepStatement.setString(2, customer.getTelephone());
            prepStatement.setString(3, customer.getBillingAccount());
            prepStatement.setInt(4, customer.getCustomerID());
            prepStatement.executeUpdate();
        }
        return true;
    }

    public void createCustomer(Customer customer) throws SQLException {
        try(Connection c = DriverManager.getConnection(Main.getDBPath());
            PreparedStatement prepStatement = c.prepareStatement("INSERT INTO customer(customer_name, address, phone_number, billing_account) VALUES (?, ?, ?, ?)")
        ){
            prepStatement.setString(1, customer.getCustomerName());
            prepStatement.setInt(2, customer.getAddressID());
            prepStatement.setString(3, customer.getTelephone());
            prepStatement.setString(4, customer.getBillingAccount());
            prepStatement.executeUpdate();
        }
    }

    /**
     * update the address field
     */
    public void setAddress(Customer customer) throws SQLException {
        try(Connection c = DriverManager.getConnection(Main.getDBPath());
            PreparedStatement prepStatement = c.prepareStatement("UPDATE customer SET address = ? WHERE customer_id = ?")
        ){
            prepStatement.setInt(1, customer.getAddressID());
            prepStatement.setInt(2, customer.getCustomerID());
            prepStatement.executeUpdate();
        }
    }

    /**
     * delete a customer
     */
    public static void deleteCustomerWithID (Customer customer) throws SQLException {

        try(Connection c = DriverManager.getConnection(Main.getDBPath());
            PreparedStatement prepStatement = c.prepareStatement("DELETE FROM customer WHERE customer_id = ?")
        ) {
            prepStatement.setInt(1, customer.getCustomerID());
            prepStatement.executeUpdate();
        }
    }
}