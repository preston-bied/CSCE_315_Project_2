import java.sql.*;
import java.util.HashMap;
import java.util.Vector;

public class queryFunctions {

    public static String salesReport(String startDate, String endDate) {
        Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 

        String sql = "SELECT saleInvoiceID, saleDate FROM saleInvoiceHistory WHERE start >= '" + startDate + "' AND end <= '" + endDate + "'";
        String output = "";
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);

            Vector<String> saleInvoiceIDs = new Vector<>();
            Vector<String> saleDates = new Vector<>();
            while (queryOutput.next()) {
                if (!saleInvoiceIDs.contains(queryOutput.getString("saleInvoiceID"))) {
                    saleInvoiceIDs.addElement(queryOutput.getString("saleInvoiceID"));
                }
            }

            Vector<String> productIDs = new Vector<>();
            Vector<String> quantitiesSold = new Vector<>();
            for (int i = 0; i < saleInvoiceIDS.size(); i++) {
                sql = "SELECT productID, quantitySold FROM saleLineItems WHERE saleInvoiceID = " + saleInvoiceIDs.elementAt(i);
                queryOutput = statement.executeQuery(sql);
                while (queryOutput.next()) {
                    productIDs.addElement(queryOutput.getString("productID"));
                    quantitiesSold.addElement(queryOutput.getString("quantitySold"));
                }
            }

            String productNames = "";
            String productIDs = "";
            String quantitiesSold = "";
            for (int i = 0; i < productIDs.size(); i++) {
                sql = "SELECT productName FROM products WHERE productID = " + productIDs.elementAt(i);
                queryOutput = statement.executeQuery(sql);
                while (queryOutput.next()) {
                    productNames += queryOutput.getString("productName") + "\n";
                    productIds += productIDs.elementAt(i) + "\n";
                    quantitiesSold += quantitiesSold.elementAt(i) + "\n";
                }
            }

            String[] output = {productIDs, productNames, quantitiesSold};
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        return output;
    }

    public static String excessReport(String startDate, String endDate) {
        Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 

        String sql = "SELECT saleInvoiceID FROM saleInvoiceHistory WHERE start >= '" + startDate + "' AND end <= '" + endDate + "'";
        String output = "";
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);

            Vector<String> invoiceIDs = new Vector<>();
            while (queryOutput.next()) {
                invoiceIDs.addElement(queryOutput.getString("saleInvoiceID"));
            }

            Vector<String> productIDs = new Vector<>();
            Vector<String> quantitiesSold = new Vector<>();
            for (int i = 0; i < invoiceIDs.size(); i++) {
                sql = "SELECT productID, quantitySold FROM saleLineItems WHERE saleInvoiceID = " + invoiceIDs.elementAt(i);
                queryOutput = statement.executeQuery(sql);
                while (queryOutput.next()) {
                    productIDs.addElement(queryOutput.getString("productID"));
                    quantitiesSold.addElement(queryOutput.getString("quantitiesSold"));
                }
            }

            HashMap<String, Double> totalQuantitiesSold = new HashMap<>();
            for (int i = 0; i < productIDs.size(); i++) {
                totalQuantitiesSold.put(productIDs.elementAt(i), 0.0);
            }
            for (int i = 0; i < productIDs.size(); i++) {
                Double quantity = Double.parseDouble(quantitiesSold.elementAt(i));
                totalQuantitiesSold.put(productIDs.elementAt(i), totalQuantitiesSold.get(productIDs.elementAt(i)) + quantity);
            }

            Vector<String> excessProductIDs = new Vector<>();
            Vector<String> excessProductNames = new Vector<>();
            Vector<String> excessProductCurrent = new Vector<>();
            Vector<String> excessProductDesired = new Vector<>();
            for (int i = 0; i < productIDs.size(); i++) {
                sql = "SELECT productName, currentStock, desiredStock FROM products WHERE productID = " + productIDs.elementAt(i);
                queryOutput = statement.executeQuery(sql);
                while (queryOutput.next()) {
                    Double currStock = Double.parseDouble(queryOutput.getString("currentStock"));
                    if (totalQuantitiesSold.get(productIDs.elementAt(i)) / currStock <= 0.1) {
                        excessProductIDs.addElement(productIDs.elementAt(i));
                        excessProductNames.addElement(queryOutput.getString("productName"));
                        excessProductCurrent.addElement(queryOutput.getString("currentStock"));
                        excessProductDesired.addElement(queryOutput.getString("desiredStock"));
                    }
                }
            }

            String excessIDs = "";
            String excessNames = "";
            String excessCurrent = "";
            String excessDesired = "";
            for (int i = 0; i < excessProductIDs.size(); i++) {
                excessIDs += excessProductIDs.elementAt(i) + "\n";
                excessNames += excessProductNames.elementAt(i) + "\n";
                excessCurrent += excessProductCurrent.elementAt(i) + "\n";
                excessDesired += excessProductDesired.elementAt(i) + "\n";
            }

            String[] output = {excessIDs, excessNames, excessCurrent, excessDesired};
            return output;
    }
}