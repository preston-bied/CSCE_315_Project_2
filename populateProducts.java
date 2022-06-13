import java.sql.*;
import java.io.*;

public class populateProducts {

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
            File file = new File("products.csv");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String delimiter = ",";
            String[] colValues;
            String line = bufferedReader.readLine();
            String lastStatement = "";

            // statement to drop old table
            String sqlStatement = "DROP TABLE products";
            stmt.execute(sqlStatement);

            // statement to create table
            sqlStatement = "CREATE TABLE products (productName VARCHAR, productID INT PRIMARY KEY, orderPrice MONEY, sellPrice MONEY, currentStock FLOAT, desiredStock FLOAT)";
            stmt.execute(sqlStatement);

            // statements to grant permissions to all team members
            sqlStatement = "GRANT ALL ON products TO csce315950_preston";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON products TO csce315950_ethan";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON products TO csce315950_cyrus";
            stmt.execute(sqlStatement);
            sqlStatement = "GRANT ALL ON products TO csce315950_rob";
            stmt.execute(sqlStatement);

            try {
                // loop through each line of the csv file and add row of items
                while (line != null) {
                    colValues = line.split(delimiter);
                    String productName = colValues[0];
                    String productID = colValues[1];
                    String orderPrice = colValues[2];
                    String sellPrice = colValues[3];
                    String currentStock = colValues[4];
                    String desiredStock = colValues[5];

                    lastStatement = sqlStatement;
                    sqlStatement = "INSERT INTO products (productName, productID, orderPrice, sellPrice, currentStock, desiredStock) VALUES(";
                    sqlStatement += "'" + productName + "', '" + productID + "', '" + orderPrice + "', '" + sellPrice + "', '" + currentStock + "', '" + desiredStock + "')";
                    if (lastStatement.equals(sqlStatement)) {
                        line = null;
                        break;
                    } else {
                        stmt.execute(sqlStatement);
                        line = bufferedReader.readLine();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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