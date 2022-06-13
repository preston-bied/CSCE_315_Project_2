import java.sql.*;
import java.io.*;

public class populateOrderLineItems {

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

            // functionality to read csv files
            File file = new File("orderLineItems.csv");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String delimiter = ",";
            String[] colValues;

            // statement to drop old table
            // String sqlStatement = "DROP TABLE orderLineItems";
            // stmt.execute(sqlStatement);

            // statement to create table
            String sqlStatement = "CREATE TABLE orderLineItems (orderLineID INT PUBLIC KEY, orderInvoiceID INT, productID INT, quantityOrdered FLOAT, FOREIGN KEY (orderInvoiceID) REFERENCES orderInvoiceHistory(orderInvoiceID), FOREIGN KEY (productID) REFERENCES products(productID))";
            stmt.execute(sqlStatement);

            // statements to grant permissions to all team members
            sqlStatement = "GRANT ALL ON orderLineItems TO csce315950_preston";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON orderLineItems TO csce315950_ethan";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON orderLineItems TO csce315950_cyrus";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON orderLineItems TO csce315950_rob";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON orderLineItems TO csce315950_2user";
            stmt.execute(sqlStatement);

            // loop through each line of the csv file and add row of items
            while (line != null) {
                colValues = line.split(delimiter);
                String orderLineID = colValues[0];
                String orderInvoiceID = colValues[1];
                String productID = colValues[2];
                String quantityOrdered = colValues[3];

                sqlStatement = "INSERT INTO orderLineItems (orderLineID, orderInvoiceID, productID, quantityOrdered) VALUES(";
                sqlStatement += "'" + orderLineID + "', '" + orderInvoiceID + "', '" + productID + "', '" + quantityOrdered + "')";
                stmt.execute(sqlStatement);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
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