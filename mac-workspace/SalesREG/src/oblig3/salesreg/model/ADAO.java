package oblig3.salesreg.model;

import oblig3.salesreg.Main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADAO {

    public Address getAddress(int addressID) throws SQLException {
        Address address;
        try(
                Connection c = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement prepStatement = c.prepareStatement("SELECT * FROM address WHERE address_id = ?")
        ){
            prepStatement.setInt(1, addressID);
            try(ResultSet result = prepStatement.executeQuery()){
                address = new Address(addressID, result.getString("street_number"), result.getString("street_name"),
                        result.getString("postal_code"), result.getString("postal_town"));
            }
        }
        return address;
    }

    public int getAddressByID(Address address) throws SQLException{
        try(
                Connection conn = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement prepStatement = conn.prepareStatement("SELECT address_id FROM address " +
                        "WHERE street_number = ? AND street_name = ? AND postal_code = ? AND postal_town = ?")
        ){
            prepStatement.setString(1, address.getStreetNumber());
            prepStatement.setString(2, address.getStreetName());
            prepStatement.setString(3, address.getPostalCode());
            prepStatement.setString(4, address.getPostalTown());
            ResultSet result = prepStatement.executeQuery();
            if(result.next())
                return result.getInt(1);
            return 0;
        }
    }

    public static List<Address> getAllAddresses() throws SQLException {
        ArrayList<Address> addresses = new ArrayList<>();
        try(
                Connection c = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement prepStatement = c.prepareStatement("SELECT * FROM address");
                ResultSet result = prepStatement.executeQuery()
        ){
            while(result.next()){
                addresses.add(new Address(result.getInt("address_id"), result.getString("street_number"),
                        result.getString("street_name"), result.getString("postal_code"),
                        result.getString("postal_town")));
            }
        }
        return addresses;
    }

    public void updateAddress(Address address) throws SQLException {
        try(
                Connection c = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement prepStatement = c.prepareStatement("UPDATE address SET street_number = ?, street_name = ?, " +
                        "postal_code = ?, postal_town = ? WHERE address_id = ?")
        ){
            prepStatement.setString(1, address.getStreetNumber());
            prepStatement.setString(2, address.getStreetName());
            prepStatement.setString(3, address.getPostalCode());
            prepStatement.setString(4, address.getPostalTown());
            prepStatement.setInt(5, address.getAddressID());
            prepStatement.executeUpdate();
        }
    }

    public void createAddress(Address address) throws SQLException {
        try(
                Connection c = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement prepStatement = c.prepareStatement("INSERT INTO address (street_number, street_name, postal_code, postal_town)" +
                        "VALUES  (?,?,?,?)")
        ){
            prepStatement.setString(1, address.getStreetNumber());
            prepStatement.setString(2, address.getStreetName());
            prepStatement.setString(3, address.getPostalCode());
            prepStatement.setString(4, address.getPostalTown());
            prepStatement.executeUpdate();
        }
    }
}