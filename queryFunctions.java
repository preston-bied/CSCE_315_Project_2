import java.sql.*;

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

            for (int i = 0; i < productIDs.size(); i++) {
                sql = "SELECT productName FROM products WHERE productID = " + productIDs.elementAt(i);
                queryOutput = statement.executeQuery(sql);
                String productName = "";
                while (queryOutput.next()) {
                    productName = queryOutput.getString("productName");
                    output += productName + quantitiesSold.elementAt(i);
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        return output;
    }
}
}