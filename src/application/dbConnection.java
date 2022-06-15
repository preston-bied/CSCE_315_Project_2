package application;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;

public class dbConnection {
    
    /**
     * 
     * @return dbConnectionString is the string used to connect to the database
     */
    public String connectToDatabase() {
		String teamNumber = "2";
		String sectionNumber = "950";
		String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
		String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
		return dbConnectionString;
  	}

    /**
     * 
     * @param dbConnectionString the string to connect to the database
     * @param queryProduct  the selected product type: chicken, beef, or pork
     * @throws IOException if scene fails to load
	 * @throws SQLException if database fails to connect
     */
    public void cashierQuery(String dbConnectionString, String queryProduct) throws IOException, SQLException{
        String sql = "SELECT productName, sellPrice, productID FROM products WHERE productName LIKE '" + 
                     queryProduct + "%' OR productName LIKE '%"+ queryProduct + "'";

        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);
            String product = "";
            String price = "";
            String ID = "";
            while (queryOutput.next()) {
            	String productName = queryOutput.getString("productName");
            	String sellPrice = queryOutput.getString("sellPrice");
            	String productID = queryOutput.getString("productID");
            	product += productName + "\n";
            	price += sellPrice + "\n";
            	ID += productID + "\n";
            }
            showProductName.setText(product);
            showProductPrice.setText(price);
            showProductID.setText(ID);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 
     * @param sceneName the name of the scene being opened
     * @throws IOException if the scene fails to load
     */
    public void launchScene(String sceneName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sceneName"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
    }

    // Not using this for now, it may be unneccessary
    public void catchException() {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }
}
