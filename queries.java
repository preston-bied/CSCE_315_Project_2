import java.sql.*;

public class queries {

    public static void main(String args[]) {
        // build connection with credentials
        Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 

        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        try {
            // create statement object
            Statement stmt = conn.createStatement();

            String sqlStatement = "SELECT saleDate FROM saleInvoiceHistory WHERE (saleInvoiceID = 56)";
            System.out.println(sqlStatement);
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                System.out.println(result.getString("saleDate"));
            }
            /*
            sqlStatement = "SELECT AVG( saleTotalCost::numeric ) FROM saleInvoiceHistory WHERE (saleDate > '2022-05-18')";
            System.out.println(sqlStatement);
            result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                System.out.println(result.getString("saleTotalCost"));
            }
            */
            sqlStatement = "SELECT SUM( saleTotalCost ) FROM saleInvoiceHistory WHERE (saleDate > '2022-05-18')";
            System.out.println(sqlStatement);
            result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                System.out.println(result.getString("saleTotalCost"));
            }
            /*
            sqlStatement = "SELECT MAX (saleTotalCost ) FROM saleInvoiceHistory WHERE (saleDate > '2021-05-25')";
            System.out.println(sqlStatement);
            result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }

            sqlStatement = "SELECT MIN (saleTotalCost ) FROM saleInvoiceHistory WHERE (saleDate > '2022-05-18')";
            System.out.println(sqlStatement);
            result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }

            sqlStatement = "SELECT productID, currentStock FROM products WHERE (currentStock < desiredStock)";
            System.out.println(sqlStatement);
            result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }

            sqlStatement = "SELECT productID, currentStock FROM products WHERE (currentStock > desiredStock)";
            System.out.println(sqlStatement);
            result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }

            sqlStatement = "SELECT employeeID FROM employees WHERE (isManager = false)";
            System.out.println(sqlStatement);
            result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }

            sqlStatement = "SELECT employeeID FROM employees WHERE (isManager = true)";
            System.out.println(sqlStatement);
            result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }
            
            sqlStatement = "INSERT INTO products (productName, productID, orderPrice, sellPrice, currentStock, desiredStock) VALUES ('porkBrain', 3019, 0.99, 2.99, 5, 5)";
            System.out.println(sqlStatement);
            int result2 = stmt.executeUpdate(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }
            
            sqlStatement = "UPDATE products SET sellPrice = 0.54 WHERE (productID = 1001)";
            System.out.println(sqlStatement);
            result2 = stmt.executeUpdate(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }

            sqlStatement = "INSERT INTO employees (employeeID, employeeName, isManager) VALUES (6, 'Michael Jackson', false)";
            System.out.println(sqlStatement);
            result2 = stmt.executeUpdate(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }

            sqlStatement = "UPDATE employees SET isManager = true WHERE (employeeID = 3)";
            System.out.println(sqlStatement);
            result2 = stmt.executeUpdate(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }

            sqlStatement = "DELETE FROM employees WHERE employeeID = 6";
            System.out.println(sqlStatement);
            result2 = stmt.executeUpdate(sqlStatement);
            while (result.next()) {
                System.out.println(result);
            }
            */

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        // close the connection
        try {
            conn.close();
            System.out.println("Connection Closed.");
            } catch(Exception e) {
             System.out.println("Connection NOT Closed.");
        }
    }
}