import java.sql.*;
import java.io.*;

public class populateAll {

    public String sqlStatement;
    public Statement stmt;

    // grant access to all team members
    public void grantAccess(String tableName) {
        sqlStatement = "GRANT ALL ON " + tableName + " TO csce315950_preston";
        stmt.execute(sqlStatement);
        sqlStatement = "GRANT ALL ON " + tableName + " TO csce315950_ethan";
        stmt.execute(sqlStatement);
        sqlStatement = "GRANT ALL ON " + tableName + " TO csce315950_cyrus";
        stmt.execute(sqlStatement);
        sqlStatement = "GRANT ALL ON " + tableName + " TO csce315950_rob";
        stmt.execute(sqlStatement);
        sqlStatement = "GRANT ALL ON " + tableName + " TO csce315950_2user";
        stmt.execute(sqlStatement);
    }

    // insert a single row of the csv file into the table
    public void insertRow(String[] rowArray, String tableName) {
        sqlStatement = "INSERT INTO " + tableName + " VALUES(";
        sqlStatement += "'" + rowArray[0] + "'";
        for (int i = 1; i < rowArray.length; i++) {
            sqlStatement += ", '" + rowArray[i] + "'";
        } 
        sqlStatement += ")";
        stmt.execute(sqlStatement);
    }

    // populate a single table entity of the database
    public String[] populateTable(String filepath, String tableName) {
        stmt = conn.createStatement();
        File file = new File(filepath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String delimiter = ",";
        String[] rowArray;
        String line = bufferedReader.readLine();

        grantAccess(stmt, tableName);

        try {  
            // loop through every line of the csv
            while (line != null) {
                rowArray = line.split(delimiter);
                insertRow(rowArray, tableName);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

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

        String[] tables = {"products", "employees", "saleLineItems", "orderLineItems", "saleInvoiceHistory", "orderInvoiceHistory"};

        // create all tables
        sqlStatement = "CREATE TABLE products (productID INT PRIMARY KEY, productName VARCHAR, orderPrice MONEY, sellPrice MONEY, currentStock FLOAT, desiredStock FLOAT)";
        stmt.execute(sqlStatement);

        sqlStatement = "CREATE TABLE employees (employeeID INT PRIMARY KEY, employeeName VARCHAR, isManager BOOL)";
        stmt.execute(sqlStatement);

        sqlStatement = "CREATE TABLE saleLineItems (saleLineID INT PRIMARY KEY, saleInvoiceID INT, productID INT, quantitySold FLOAT, FOREIGN KEY (saleInvoiceID) REFERENCES saleInvoiceHistory(saleInvoiceID), FOREIGN KEY (productID) REFERENCES products(productID))";
        stmt.execute(sqlStatement);

        sqlStatement = "CREATE TABLE orderLineItems (orderLineID INT PUBLIC KEY, orderInvoiceID INT, productID INT, quantityOrdered FLOAT, FOREIGN KEY (orderInvoiceID) REFERENCES orderInvoiceHistory(orderInvoiceID), FOREIGN KEY (productID) REFERENCES products(productID))";
        stmt.execute(sqlStatement);

        sqlStatement = "CREATE TABLE saleInvoiceHistory (saleInvoiceID INT PRIMARY KEY, saleDate DATE, saleTotalCost MONEY, employeeID INT)";
        stmt.execute(sqlStatement);

        sqlStatement = "CREATE TABLE orderInvoiceHistory (orderInvoiceID INT PRIMARY KEY, orderDate DATE, orderTotalCost MONEY, employeeID INT, distributorID INT)";
        stmt.execute(sqlStatement);

        // populate each table of the database
        String filepath;
        for (int i = 0; i < tables.length; i++) {
            filepath = "CSV/" + tables[i];
            populateTable(filepath, tables[i]);
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