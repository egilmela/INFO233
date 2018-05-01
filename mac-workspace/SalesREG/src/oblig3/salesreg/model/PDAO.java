package oblig3.salesreg.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oblig3.salesreg.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;


public class PDAO {

        public Product getProduct(int id) throws SQLException {
            Product product;
            try(
                    Connection conn = DriverManager.getConnection(Main.getDBPath());
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM product, category WHERE product_id = ? AND category = category_id")
            ){
                stmt.setInt(1, id);
                try(ResultSet result = stmt.executeQuery()) {
                    product = new Product(id, result.getString("product_name"), result.getString("description"),
                            result.getInt("price"), result.getInt("category"));
                }
            }
            return product;
        }

        public ObservableList<Product> getAllProducts() throws SQLException {
            ObservableList<Product> products = FXCollections.observableArrayList();
            try(
                    Connection conn = DriverManager.getConnection(Main.getDBPath());
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM product, category where category = category_id");
                    ResultSet result = stmt.executeQuery()
            ){
                while(result.next()){
                    products.add(new Product(result.getInt("product_id"), result.getString("product_name"), result.getString("description"),
                            result.getInt("price"), result.getInt("category")));
                }
            }
            return products;
        }


        /*
        /**
         * Create a product. If the product category does not exist, create it.
         */

    /*
    public void createProduct(Product product) throws SQLException {
        try(
                Connection conn = DriverManager.getConnection(Main.getDBPath());
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO product(product_name, description, price, category) VALUES (?,?,?,?)");
                PreparedStatement categoryStmt = conn.prepareStatement("INSERT INTO category(category_name) VALUES (?)")
        ){
            int categoryID = getCategoryId(product.getCategory(), conn);
            if(categoryID == 0){
                categoryStmt.setString(1, product.getCategory());
                categoryStmt.executeUpdate();
                categoryID = getCategoryId(product.getCategory(), conn);
            }
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductDescription());
            stmt.setInt(3, product.getPrice());
            stmt.setInt(4, categoryID);
            stmt.executeUpdate();
        }
    }

    /*


    /**
     * Update a product. If the product category does not exist, create it.
     */

    /*
        public void updateProduct(Product product) throws SQLException {
            try(
                    Connection conn = DriverManager.getConnection(Main.getDBPath());
                    PreparedStatement stmt = conn.prepareStatement("UPDATE product SET product_name = ?, description = ?, price = ?, category = ? WHERE product_id = ?");
                    PreparedStatement categoryStmt = conn.prepareStatement("INSERT INTO category(category_name) VALUES (?)")
            ){
                int categoryID = getCategoryId(product.getCategory(), conn);
                if(categoryID == 0){
                    categoryStmt.setString(1, product.getCategory());
                    categoryStmt.executeUpdate();
                    categoryID = getCategoryId(product.getCategory(), conn);
                }
                stmt.setString(1, product.getProductName());
                stmt.setString(2, product.getProductDescription());
                stmt.setInt(3, product.getPrice());
                stmt.setInt(4, categoryID);
                stmt.setInt(5, product.getProductID());
                stmt.executeUpdate();
            }
        }


    */

   /**
    * @return 0 if category does not exist, else returns category id
    */
        private int getCategoryId(String categoryName, Connection conn) throws SQLException {
            try(
                    PreparedStatement stmt = conn.prepareStatement("SELECT category_id FROM category WHERE category_name = ?")
            ){
                stmt.setString(1, categoryName);
                ResultSet result = stmt.executeQuery();
                if(result.next()){
                    return result.getInt(1);
                }
            }
            return 0;
        }


        public TreeMap<Integer, String> getAllCategories() throws SQLException {
            TreeMap<Integer, String> categories = new TreeMap<>();
            try(
                    Connection conn = DriverManager.getConnection(Main.getDBPath());
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM category");
                    ResultSet result = stmt.executeQuery()
            ) {
                while (result.next()){
                    categories.put(result.getInt(1), result.getString(2));
                }
            }

            return categories;

        }


        /*

        public List<Product> getProductsByCategory(int id) throws SQLException {
            List<Product> products = new ArrayList<>();
            try(
                    Connection conn = DriverManager.getConnection(Main.getDBPath());
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM product, category where category = category_id AND category_id = ?")
            ){
                stmt.setInt(1, id);

                try(ResultSet result = stmt.executeQuery()) {
                    while (result.next()) {
                        products.add(new Product(result.getInt("product_id"), result.getString("product_name"),
                                result.getString("description"), result.getInt("price"),
                                result.getString("category_name")));
                    }
                }
            }
            return products;
        }

        */




    }