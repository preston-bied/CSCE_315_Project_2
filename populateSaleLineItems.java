import java.sql.*;
import java.io.*;

public class populateSaleLineItems {

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
            File file = new File("saleLineItems.csv");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String delimiter = ",";
            String[] colValues;

            // statement to drop old table
            String sqlStatement = "DROP TABLE saleLineItems";
            stmt.execute(sqlStatement);

            // statement to create table
            sqlStatement = "CREATE TABLE saleLineItems (saleLineID INT PRIMARY KEY, saleInvoiceID INT, productID INT, employeeID INT, quantitySold FLOAT, FOREIGN KEY (saleInvoiceID) REFERENCES saleInvoiceHistory(saleInvoiceID), FOREIGN KEY (productID) REFERENCES products(productID))";
            stmt.execute(sqlStatement);

            // statements to grant permissions to all team members
            sqlStatement = "GRANT ALL ON saleLineItems TO csce315950_preston";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON saleLineItems TO csce315950_ethan";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON saleLineItems TO csce315950_cyrus";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON saleLineItems TO csce315950_rob";
            stmt.execute(sqlStatement);

            // loop through each line of the csv file and add row of items
            while (line != null) {
                colValues = line.split(delimiter);
                String saleLineID = colValues[0];
                String saleInvoiceID = colValues[1];
                String productID = colValues[2];
                String employeeID = colValues[3];
                String quantitySold = colValues[4];

                sqlStatement = "INSERT INTO saleLineItems (saleLineID, saleInvoiceID, productID, employeeID, quantitySold) VALUES(";
                sqlStatement += "'" + saleLineID + "', '" + saleInvoiceID + "', '" + productID + "', '" + employeeID + "', '" + quantitySold + "')";
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