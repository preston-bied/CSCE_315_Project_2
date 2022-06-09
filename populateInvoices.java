import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class populateInvoices {

    public static void main(String args[]) {
        Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 

        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");

        try {
            Statement stmt = conn.createStatement();
            File file = new File("testData.csv");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String delimiter = ",";
            String[] colValues;

            String sqlStatement = "CREATE TABLE IF NOT EXISTS invoices (productName VARCHAR, productID INT PRIMARY KEY, orderPrice MONEY, sellPrice MONEY, currentStock FLOAT, desiredStock FLOAT, quantitySoldYTD FLOAT)";
            stmt.execute(sqlStatement);

            sqlStatement = "GRANT ALL ON products TO csce315950_preston";
            stmt.execute(sqlStatement);

            while (line != null) {
                colValues = line.split(delimiter);
                sqlStatement = "INSERT INTO products (productName, productID, orderPrice, sellPrice, currentStock, desiredStock, quantitySoldYTD) VALUES(";
                sqlStatement += "'" + colValues[0] + "', '" + colValues[1] + "', '" + colValues[2] + "', '" + colValues[3] + "', '" + colValues[4] + "', '" + colValues[5] + "', '" + colValues[6] + "')";
                stmt.execute(sqlStatement);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            int result = stmt.executeUpdate(sqlStatement);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        try {
            conn.close();
            System.out.println("Connection Closed.");
            } catch(Exception e) {
             System.out.println("Connection NOT Closed.");
        }
    }
}