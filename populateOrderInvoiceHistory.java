import java.sql.*;
import java.io.*;

public class populateOrderInvoiceHistory {

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
            File file = new File("orderInvoiceHistory.csv");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String delimiter = ",";
            String[] colValues;

            // statement to drop old table
            // String sqlStatement = "DROP TABLE orderInvoiceHistory";
            // stmt.execute(sqlStatement);

            // statement to create table
            String sqlStatement = "CREATE TABLE orderInvoiceHistory (orderInvoiceID INT PRIMARY KEY, orderDate DATE, orderTotalCost MONEY, distributorID INT)";
            stmt.execute(sqlStatement);

            // statements to grant permissions to all team members
            sqlStatement = "GRANT ALL ON orderInvoiceHistory TO csce315950_preston";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON orderInvoiceHistory TO csce315950_ethan";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON orderInvoiceHistory TO csce315950_cyrus";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON orderInvoiceHistory TO csce315950_rob";
            stmt.execute(sqlStatement);

            // loop through each line of the csv file and add row of items
            while (line != null) {
                colValues = line.split(delimiter);
                String orderInvoiceID = colValues[0];
                String orderDate = colValues[1];
                String orderTotalCost = colValues[2];
                String distributorID = colValues[3];

                sqlStatement = "INSERT INTO orderInvoiceHistory (orderInvoiceID, orderDate, orderTotalCost, distributorID) VALUES(";
                sqlStatement += "'" + orderInvoiceID + "', '" + orderDate + "', '" + orderTotalCost + "', '" + distributorID + "')";
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