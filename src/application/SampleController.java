package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class SampleController {

	//Scene variables
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	//controllers
	@FXML private Button cashierButton;
	@FXML private Button managerButton;
	@FXML private Button managerOrderButton;
	@FXML private Button logoutButton;
	@FXML private Button addItemButton;
	//@FXML private Button addOrderButton;
	@FXML private AnchorPane scenePane;
	@FXML private Label showProductName = new Label();
	@FXML private Label showProductPrice = new Label();
	@FXML private Label showProductID = new Label();
	@FXML private Label showProductCurrentStock = new Label();
	@FXML private Label showProductDesiredStock = new Label();	
	@FXML private Label saleItems = new Label();
	@FXML private Label orderItems = new Label();
	@FXML private Label runningTotal = new Label();
	@FXML private Label orderItemSPLabel = new Label();
	@FXML private TextField runningOrderTotal = new TextField();
	@FXML private TextField addItemField = new TextField();
	@FXML private TextField weightField = new TextField();
	@FXML private TextField orderIDField = new TextField();
	@FXML private TextField orderWeightField = new TextField();
	@FXML private TextField distributorField = new TextField();
	@FXML private ScrollPane scrollPane = new ScrollPane();
	
	//manager invoice history page
	@FXML private Label managerIDLabel = new Label();
	@FXML private Label managerNameLabel = new Label();
	@FXML private Label managerSDLabel = new Label();
	@FXML private Label managerQuantityLabel = new Label();
	@FXML private Label managerCurrentLabel = new Label();
	@FXML private Label managerDesiredLabel = new Label();
	@FXML private TextField startDateField = new TextField();
	@FXML private TextField endDateField = new TextField();	
	
	//manager inventory page
	//@FXML private Label managerIdInventoryLabel = new Label();
	//@FXML private Label managerNameInventoryLabel = new Label();
	@FXML private Label managerOPInventoryLabel = new Label();
	@FXML private Label managerSPInventoryLabel = new Label();
	@FXML private Label managerCurrentInventoryLabel = new Label();
	@FXML private Label managerDesiredInventoryLabel = new Label();
	
	@FXML private TextField managerIdInventoryField = new TextField();
	@FXML private TextField managerNameInventoryField = new TextField();
	@FXML private TextField managerOPInventoryField = new TextField();
	@FXML private TextField managerSPInventoryField = new TextField();
	@FXML private TextField managerCurrentInventoryField = new TextField();
	@FXML private TextField managerDesiredInventoryField = new TextField();
	
	
	
	public String sale = "";
	public Double saleTotal = 0.0;
	public int currSaleInvoiceID = 0;
	public int currLineItemID = 0;
	public int employeeID = 1;
	public String currDate = "2022-06-15";
	public Vector<String> productIDs = new Vector<>();
	public Vector<Integer> lineItemIDs = new Vector<>();
	public Vector<Double> weights = new Vector<>();
	
	public String order = "";
	public Double orderTotal = 0.0;
	public int currOrderInvoiceID = 0;
	public int currOrderLineItemID = 0;
	public int orderEmployeeID = 1; 
	public String currOrderDate = "2022-06-15";
	public Vector<String> orderProductIDs = new Vector<>();
	public Vector<Integer> orderLineItemIDs = new Vector<>();
	public Vector<Double> orderWeights = new Vector<>();
	
	public Vector<String> deleteID = new Vector<>();
	public Vector<String> deleteWeight = new Vector<>();
	
	/**
	 * @param event launches cashier interface
	 * @throws IOException if scene fails to load
	 */
	public void cashierLaunch(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("cashierScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	/**
	 * @param event pulls up chicken info in cashier interface
	 * @throws IOException if scene fails to load
	 * @throws SQLException if database fails to connect
	 */
	public void cashierChickenQuery(ActionEvent event) throws IOException, SQLException {
		
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "SELECT productName, sellPrice, productID FROM products WHERE productName LIKE 'Chicken%' OR productName LIKE '%Chicken'";
        
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
	 * @param event pulls up beef info for cashier interface
	 * @throws IOException if scene fails to launch
	 */
	public void cashierBeefQuery(ActionEvent event) throws IOException {
		
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "SELECT productName, sellPrice, productID FROM products WHERE productName LIKE 'Beef%' OR productName LIKE '%Beef'";
        
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
	 * @param event pulls up beef info for cashier interface
	 * @throws IOException if scene fails to launch
	 */
	public void cashierPorkQuery(ActionEvent event) throws IOException {
		
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "SELECT productName, sellPrice, productID FROM products WHERE productName LIKE 'Pork%' OR productName LIKE '%Pork'";
        
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
	 * @param event pulls up chicken info for cashier interface
	 * @throws IOException if scene fails to launch
	 */
	public void managerChickenQuery(ActionEvent event) throws IOException, SQLException {
		
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "SELECT productName, orderPrice, productID, currentStock, desiredStock FROM products WHERE productName LIKE 'Chicken%' OR productName LIKE '%Chicken'";
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);
            String product = "";
            String price = "";
            String ID = "";
            String currStock = "";
            String desirStock = "";
            
            while (queryOutput.next()) {
            	String productName = queryOutput.getString("productName");
            	String orderPrice = queryOutput.getString("orderPrice");
            	String productID = queryOutput.getString("productID");
                String currentStock = queryOutput.getString("currentStock");
                String desiredStock = queryOutput.getString("desiredStock");
            	product += productName + "\n";
            	price += orderPrice + "\n";
            	ID += productID + "\n";
            	currStock += currentStock + "\n";
            	desirStock += desiredStock + "\n";
            	
            }
            showProductName.setText(product);
            showProductPrice.setText(price);
            showProductID.setText(ID);
            showProductCurrentStock.setText(currStock);
            showProductDesiredStock.setText(desirStock);
            
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
	}	
	
	/**
	 * 
	 * @param event pulls up beef info for cashier interface
	 * @throws IOException if scene fails to launch
	 */
	public void managerBeefQuery(ActionEvent event) throws IOException {
		
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "SELECT productName, orderPrice, productID, currentStock, desiredStock FROM products WHERE productName LIKE 'Beef%' OR productName LIKE '%Beef'";
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);
            String product = "";
            String price = "";
            String ID = "";
            String currStock = "";
            String desirStock = "";
            
            while (queryOutput.next()) {
            	String productName = queryOutput.getString("productName");
            	String orderPrice = queryOutput.getString("orderPrice");
            	String productID = queryOutput.getString("productID");
                String currentStock = queryOutput.getString("currentStock");
                String desiredStock = queryOutput.getString("desiredStock");
            	product += productName + "\n";
            	price += orderPrice + "\n";
            	ID += productID + "\n";
            	currStock += currentStock + "\n";
            	desirStock += desiredStock + "\n";
            	
            }
            showProductName.setText(product);
            showProductPrice.setText(price);
            showProductID.setText(ID);
            showProductCurrentStock.setText(currStock);
            showProductDesiredStock.setText(desirStock);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

	}	
	
	/**
	 * 
	 * @param event pulls up beef info for cashier interface
	 * @throws IOException if scene fails to launch
	 */
	public void managerPorkQuery(ActionEvent event) throws IOException {
		
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "SELECT productName, orderPrice, productID, currentStock, desiredStock FROM products WHERE productName LIKE 'Pork%' OR productName LIKE '%Pork'";
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);
            String product = "";
            String price = "";
            String ID = "";
            String currStock = "";
            String desirStock = "";
            
            while (queryOutput.next()) {
            	String productName = queryOutput.getString("productName");
            	String orderPrice = queryOutput.getString("orderPrice");
            	String productID = queryOutput.getString("productID");
                String currentStock = queryOutput.getString("currentStock");
                String desiredStock = queryOutput.getString("desiredStock");
            	product += productName + "\n";
            	price += orderPrice + "\n";
            	ID += productID + "\n";
            	currStock += currentStock + "\n";
            	desirStock += desiredStock + "\n";
            	
            }
            showProductName.setText(product);
            showProductPrice.setText(price);
            showProductID.setText(ID);
            showProductCurrentStock.setText(currStock);
            showProductDesiredStock.setText(desirStock);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
	}
	
	// TODO
	public void cashierDeleteButton(ActionEvent event) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO saleInvoiceHistory VALUES (" + currSaleInvoiceID + ", '" + currDate + "', '" + saleTotal + "', " + employeeID + ")";
            statement.execute(sql);
            for (int i = 0; i < weights.size(); i++) {
            	sql = "INSERT INTO saleLineItems VALUES (" + lineItemIDs.elementAt(i) + ", " + currSaleInvoiceID + ", " + productIDs.elementAt(i) + ", " + weights.elementAt(i) + ")";
                statement.execute(sql);
            }
            saleTotal = 0.0;
            currSaleInvoiceID = 0;
            currLineItemID = 0;
            sale = "";
            saleItems.setText(sale);
            lineItemIDs.clear();
            productIDs.clear();
            weights.clear();
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        runningTotal.setText("$0");
        addItemField.setText("");
        weightField.setText("");
        
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmed");
		alert.setHeaderText("Deletion Completed");
	}
	
	
	/**
	 * 
	 * @param event launches manager interface
	 * @throws IOException if scene fails to launch
	 */
	public void managerLaunch(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("managerScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	/**
	 * 
	 * @param event launches manager orders scene interface
	 * @throws IOException if scene fails to launch
	 * TODO FINISH ME!!!
	 */
	public void managerOrders(ActionEvent event) throws IOException {		
		//System.out.println("Launched manager order page");
		Parent root = FXMLLoader.load(getClass().getResource("managerCreateOrder.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 		
	}
	/**
	 * 
	 * @param event opens manager inventory scene
	 * @throws IOException if scene fails to launch
	 */
	public void managerInventoryLaunch(ActionEvent event) throws IOException {		
		//System.out.println("Launched manager order page");
		Parent root = FXMLLoader.load(getClass().getResource("managerInventory.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 		
	}
	/**
	 * 
	 * @param event opens manager history scene 
	 * @throws IOException if scene fails to launch
	 */
	public void managerHistoryLaunch(ActionEvent event) throws IOException {		
		//System.out.println("Launched manager order page");
		Parent root = FXMLLoader.load(getClass().getResource("managerHistory.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 	
	}
	
	
	public void managerSaleReport(ActionEvent event ) throws IOException {
		managerCurrentLabel.setText("");
		managerDesiredLabel.setText("");
		String[] columnVals = queryActions.salesReport(startDateField.getText(), endDateField.getText());
		managerIDLabel.setText(columnVals[1]);
		managerNameLabel.setText(columnVals[0]);
		managerQuantityLabel.setText(columnVals[2]);
	}
        
	public void managerExcessReport(ActionEvent event ) throws IOException {
		managerQuantityLabel.setText("");
		String[] columnVals = queryActions.excessReport(startDateField.getText(), endDateField.getText());
		managerIDLabel.setText(columnVals[0]);
		managerNameLabel.setText(columnVals[1]);
		managerCurrentLabel.setText(columnVals[2]);
		managerDesiredLabel.setText(columnVals[3]);
	}     
	
	
	public void managerRestockReport(ActionEvent event ) throws IOException {
		managerQuantityLabel.setText("");
		String[] columnVals = queryActions.restockReport(startDateField.getText(), endDateField.getText());
		managerIDLabel.setText(columnVals[0]);
		managerNameLabel.setText(columnVals[1]);
		managerCurrentLabel.setText(columnVals[2]);
		managerDesiredLabel.setText(columnVals[3]);
	}
	/**
	 * 
	 * @param event returns manager to selection window
	 * @throws IOException if scene fails to launch
	 */
	
	public void managerRefresh(ActionEvent event) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "SELECT productID, productName, orderPrice, sellPrice, currentStock, desiredStock FROM products";
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);
            
            String IDs = "";
            String names = "";
            String orderPrice = "";
            String salePrice = "";
            String currentStock = "";
            String desiredStock = "";
            
            while (queryOutput.next()) {
            	IDs += queryOutput.getString("productID") + "\n";
            	names += queryOutput.getString("productName") + "\n";
            	orderPrice += queryOutput.getString("orderPrice") + "\n";
            	salePrice += queryOutput.getString("sellPrice") + "\n";
            	currentStock += queryOutput.getString("currentStock") + "\n";
            	desiredStock += queryOutput.getString("desiredStock") + "\n";
            }
            
            managerIDLabel.setText(IDs);
            managerNameLabel.setText(names);
            managerOPInventoryLabel.setText(orderPrice);
            managerSPInventoryLabel.setText(salePrice);
            managerCurrentLabel.setText(currentStock);
            managerDesiredLabel.setText(desiredStock);
            
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
	}
	
	public void managerUpdateName(ActionEvent event) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "UPDATE products SET productName = '" + managerNameInventoryField.getText() + "' WHERE productID = " + managerIdInventoryField.getText();
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            statement.execute(sql);
            
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmed");
		alert.setHeaderText("Name Successfully Updated");
		
		//launch logout alert from either interface
		if( alert.showAndWait().get() == ButtonType.OK) {
	        managerIdInventoryField.setText("");
	        managerNameInventoryField.setText("");
	        managerOPInventoryField.setText("");
	        managerSPInventoryField.setText("");
	        managerCurrentInventoryField.setText("");
	        managerDesiredInventoryField.setText("");
		}
	}

	public void managerUpdateOrderPrice(ActionEvent event) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "UPDATE products SET orderPrice = '" + managerOPInventoryField.getText() + "' WHERE productID = " + managerIdInventoryField.getText();
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            statement.execute(sql);
            
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmed");
		alert.setHeaderText("Order Price Successfully Updated");
		
		//launch logout alert from either interface
		if( alert.showAndWait().get() == ButtonType.OK) {
	        managerIdInventoryField.setText("");
	        managerNameInventoryField.setText("");
	        managerOPInventoryField.setText("");
	        managerSPInventoryField.setText("");
	        managerCurrentInventoryField.setText("");
	        managerDesiredInventoryField.setText("");
		}
	}

	public void managerUpdateSalePrice(ActionEvent event) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "UPDATE products SET sellPrice = '" + managerSPInventoryField.getText() + "' WHERE productID = " + managerIdInventoryField.getText();
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            statement.execute(sql);
            
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmed");
		alert.setHeaderText("Item Price Successfully Updated");
		
		//launch logout alert from either interface
		if( alert.showAndWait().get() == ButtonType.OK) {
	        managerIdInventoryField.setText("");
	        managerNameInventoryField.setText("");
	        managerOPInventoryField.setText("");
	        managerSPInventoryField.setText("");
	        managerCurrentInventoryField.setText("");
	        managerDesiredInventoryField.setText("");
		}
		
	}
	
	public void managerCurrentQTY(ActionEvent event) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "UPDATE products SET currentStock = '" + managerCurrentInventoryField.getText() + "' WHERE productID = " + managerIdInventoryField.getText();
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            statement.execute(sql);
            
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmed");
		alert.setHeaderText("Quantity Successfully Updated");
		
		//launch logout alert from either interface
		if( alert.showAndWait().get() == ButtonType.OK) {
	        managerIdInventoryField.setText("");
	        managerNameInventoryField.setText("");
	        managerOPInventoryField.setText("");
	        managerSPInventoryField.setText("");
	        managerCurrentInventoryField.setText("");
	        managerDesiredInventoryField.setText("");
		}
	}

	public void managerDesiredQTY(ActionEvent event) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "UPDATE products SET desiredStock = '" + managerDesiredInventoryField.getText() + "' WHERE productID = " + managerIdInventoryField.getText();
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            statement.execute(sql);
            
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmed");
		alert.setHeaderText("Desired Stock Successfully Updated");
		
		//launch logout alert from either interface
		if( alert.showAndWait().get() == ButtonType.OK) {
	        managerIdInventoryField.setText("");
	        managerNameInventoryField.setText("");
	        managerOPInventoryField.setText("");
	        managerSPInventoryField.setText("");
	        managerCurrentInventoryField.setText("");
	        managerDesiredInventoryField.setText("");
		}
	}
	
	public void managerNewItem(ActionEvent event) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "INSERT INTO products VALUES ('" + managerIdInventoryField.getText() + "', '" + managerNameInventoryField.getText()
        	+ "', '" + managerOPInventoryField.getText() + "', '" + managerSPInventoryField.getText() + "', '" +
        	managerCurrentInventoryField.getText() + "', '" + managerDesiredInventoryField.getText() + "')";
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            statement.execute(sql);
            
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }     
        
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmed");
		alert.setHeaderText("Add Item Completed");
		
		//launch logout alert from either interface
		if( alert.showAndWait().get() == ButtonType.OK) {
	        managerIdInventoryField.setText("");
	        managerNameInventoryField.setText("");
	        managerOPInventoryField.setText("");
	        managerSPInventoryField.setText("");
	        managerCurrentInventoryField.setText("");
	        managerDesiredInventoryField.setText("");
		}
	}
	
	public void backButton(ActionEvent event) throws IOException {		
		//System.out.println("Launched manager order page");
		Parent root = FXMLLoader.load(getClass().getResource("managerScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 		
	}	
	
	/**
	 * 
	 * @param event launches the logout alert prompt
	 * @throws IOException if alert prompt fails to launch
	 */
	public void logout( ActionEvent event ) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout");
		alert.setContentText("Do you wish to continue?");		
		
		//launch logout alert from either interface
		if( alert.showAndWait().get() == ButtonType.OK) {
			Parent root = FXMLLoader.load(getClass().getResource("homePageGUI.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			System.out.println("You have successfully logged out");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show(); 		
		}
	}
	
	/**
	 * 
	 * @param event adds item it cashier interface for customer total
	 * @throws IOException if scene fails to launches
	 */
	public void addItem (ActionEvent event) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String productID = addItemField.getText();
        productIDs.addElement(productID);
        Double weight = Double.parseDouble( weightField.getText() );
        weights.addElement(weight);
        String sql = "SELECT productName, sellPrice, currentStock FROM products WHERE productID = " + productID;
        
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);
            
            String productName ="";
            String productPricePerKg = "";
            
            while (queryOutput.next()) {
            	productName = queryOutput.getString("productName");
            	productPricePerKg = queryOutput.getString("sellPrice");
            }
            
            Double totalPrice = Double.parseDouble(productPricePerKg.substring(1)) * weight;
            DecimalFormat df = new DecimalFormat("#.##");
            totalPrice = Double.parseDouble(df.format(totalPrice));
            
            saleTotal += totalPrice;
            runningTotal.setText("$" + saleTotal);
            
            sale += productName + " | $" + totalPrice + "\n";
            saleItems.setText(sale);
            
            if (currLineItemID == 0) {
            	 sql = "SELECT MAX (saleLineID) FROM saleLineItems";
                 queryOutput = statement.executeQuery(sql);
              
                 String maxID = "";
                 while (queryOutput.next()) {
                 	maxID = queryOutput.getString("max");
                 }
                 currLineItemID = Integer.parseInt(maxID);
            }
            currLineItemID += 1;
            lineItemIDs.addElement(currLineItemID);
            
            if (currSaleInvoiceID == 0) {
            	sql = "SELECT MAX (saleInvoiceID) FROM saleLineItems";
            	queryOutput = statement.executeQuery(sql);
            	String maxSaleInvoiceID = "";
            	while (queryOutput.next()) {
            		maxSaleInvoiceID = queryOutput.getString("max");
            	}
            	currSaleInvoiceID = Integer.parseInt(maxSaleInvoiceID) + 1;
            	
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
	}
	
	/**
	 * 
	 * @param event completes a sale in cashier interface and uploads the database
	 * @throws IOException if scene fails to launch
	 */
	public void completeSale( ActionEvent event ) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            
            String sql = "INSERT INTO saleInvoiceHistory VALUES (" + currSaleInvoiceID + ", '" + currDate + "', '" + saleTotal + "', " + employeeID + ")";
            statement.execute(sql);
            for (int i = 0; i < weights.size(); i++) {
                sql = "UPDATE products SET currentStock = currentStock - " + weights.elementAt(i) + " WHERE productID = " + productIDs.elementAt(i);
                statement.execute(sql);
            	sql = "INSERT INTO saleLineItems VALUES (" + lineItemIDs.elementAt(i) + ", " + currSaleInvoiceID + ", " + productIDs.elementAt(i) + ", " + weights.elementAt(i) + ")";
                statement.execute(sql);
            }
            saleTotal = 0.0;
            currSaleInvoiceID = 0;
            currLineItemID = 0;
            sale = "";
            saleItems.setText(sale);
            lineItemIDs.clear();
            productIDs.clear();
            weights.clear();
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

	}
	
	/**
	 * 
	 * @param event adds an item to order list in managers order page interface
	 * @throws IOException if scene fails to launch
	 */
	public void addOrderItem (ActionEvent event) throws IOException {
		//System.out.println("Launched manager order item button");
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String productID = orderIDField.getText();
        orderProductIDs.addElement(productID);
        Double weight = Double.parseDouble( orderWeightField.getText() );
        orderWeights.addElement(weight);
        String sql = "SELECT productName, orderPrice FROM products WHERE productID = " + productID;
        
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);
            
            String productName ="";
            String productPricePerKg = "";
            
            while (queryOutput.next()) {
            	productName = queryOutput.getString("productName");
            	productPricePerKg = queryOutput.getString("orderPrice");
            }
            
            //populate in a while loop using queryOutput
            //scrollPane.setContent(orderItemSPLabel);
            //orderItemSPLabel.setText();
            
            Double totalPrice = Double.parseDouble(productPricePerKg.substring(1)) * weight;
            DecimalFormat df = new DecimalFormat("#.##");
            totalPrice = Double.parseDouble(df.format(totalPrice));
            orderTotal += totalPrice;
            runningOrderTotal.setText("$" + orderTotal);
            sql = "UPDATE products SET currentStock = currentStock + " + weight + " WHERE productID = " + productID;
            statement.execute(sql);
            
            
            order += productName + " | $" + totalPrice + "\n";
            System.out.println(order);
            orderItems.setText(order);
            
            if (currOrderLineItemID == 0) {
            	 sql = "SELECT MAX (orderLineID) FROM orderLineItems";
                 queryOutput = statement.executeQuery(sql);
              
                 String maxID = "";
                 while (queryOutput.next()) {
                 	maxID = queryOutput.getString("max");
                 }
                 currOrderLineItemID = Integer.parseInt(maxID);
            }
            currOrderLineItemID += 1;
            orderLineItemIDs.addElement(currOrderLineItemID);
            
            if (currOrderInvoiceID == 0) {
            	sql = "SELECT MAX (orderInvoiceID) FROM orderInvoiceHistory";
            	queryOutput = statement.executeQuery(sql);
            	String maxOrderInvoiceID = "";
            	while (queryOutput.next()) {
            		maxOrderInvoiceID = queryOutput.getString("max");
            	}
            	currOrderInvoiceID = Integer.parseInt(maxOrderInvoiceID);
            }
            currOrderInvoiceID++;
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }   
	}
	
	/**
	 * 
	 * @param event completes an order in managers order page interface
	 * @throws IOException if scene fails to load
	 */
	public void completeOrder( ActionEvent event ) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            
            String distributor = distributorField.getText();
            
            String sql = "INSERT INTO orderInvoiceHistory VALUES (" + currOrderInvoiceID + ", '" + currOrderDate + "', '" + orderTotal + "', " + orderEmployeeID + ", " + distributor + ")";
            statement.execute(sql);
            for (int i = 0; i < orderWeights.size(); i++) {
            	sql = "INSERT INTO orderLineItems VALUES (" + orderLineItemIDs.elementAt(i) + ", " + currOrderInvoiceID + ", " + orderProductIDs.elementAt(i) + ", " + orderWeights.elementAt(i) + ")";
                statement.execute(sql);
            }
            orderTotal = 0.0;
            currOrderInvoiceID = 0;
            currOrderLineItemID = 0;
            order = "";
            orderItems.setText(order);
            orderLineItemIDs.clear();
            orderProductIDs.clear();
            orderWeights.clear();
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
 
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmed");
		alert.setHeaderText("Order Completed");
		//alert.setContentText("");	
		
		//launch logout alert from either interface
		if( alert.showAndWait().get() == ButtonType.OK) {
		       runningOrderTotal.setText("$0");
		        orderIDField.setText("");
		        orderWeightField.setText("");
		        distributorField.setText("");
		}
	}

	public void populateInventory( ActionEvent event ) throws IOException {
		Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        String sql = "SELECT productName, productID, currentStock, desiredStock FROM products";
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);
            
            String productsList = "";
            while (queryOutput.next()) {
            	productsList += queryOutput.getString("productID") + " | " + queryOutput.getString("productName") + " | curr: " 
                + queryOutput.getString("currentStock") + " | des: " + queryOutput.getString("desiredStock") + "\n";
            	
            }
         // System.out.println(productsList);
            orderItemSPLabel.setText(productsList);
            scrollPane.setContent(orderItemSPLabel);
           
        } catch ( Exception e ) {
        	e.printStackTrace();
        	System.err.println(e.getClass().getName()+": "+e.getMessage());
        	System.exit(0);
        }
	}



}// end SampleController class
