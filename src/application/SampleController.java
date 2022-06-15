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
	@FXML private Label saleItems = new Label();
	@FXML private Label orderItems = new Label();
	@FXML private Label runningTotal = new Label();
	@FXML private TextField runningOrderTotal = new TextField();
	@FXML private TextField addItemField = new TextField();
	@FXML private TextField weightField = new TextField();
	@FXML private TextField orderIDField = new TextField();
	@FXML private TextField orderWeightField = new TextField();
	@FXML private TextField distributorField = new TextField();
	
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
	
	/**
	 * @param event launches cashier interface
	 * @throws IOException if scene fails to load
	 */
	public void cashierLaunch(ActionEvent event) throws IOException {
		dbConnection newConnection = new dbConnection(); 
        newConnection.launchScene("cashierScene.fxml");
	}
	
	/**
	 * @param event pulls up chicken info in cashier interface
	 * @throws IOException if scene fails to load
	 * @throws SQLException if database fails to connect
	 */
	public void cashierChickenQuery(ActionEvent event) throws IOException, SQLException {
		
		Connection conn = null;
        String dbConnectionString = dbConnection.connectToDatabase(); 
        newConnection.cashierQuery(dbConnectionString, "Chicken");
	}	
	
	/**
	 * 
	 * @param event pulls up beef info for cashier interface
	 * @throws IOException if scene fails to launch
	 */
	public void cashierBeefQuery(ActionEvent event) throws IOException {
		
		Connection conn = null;
        String dbConnectionString = dbConnection.connectToDatabase(); 
        newConnection.cashierQuery(dbConnectionString, "Beef");

	}	
	
	/**
	 * 
	 * @param event pulls up beef info for cashier interface
	 * @throws IOException if scene fails to launch
	 */
	public void cashierPorkQuery(ActionEvent event) throws IOException {
		
		Connection conn = null;
        String dbConnectionString = dbConnection.connectToDatabase(); 
        newConnection.cashierQuery(dbConnectionString, "Pork");
	}	
	
	/**
	 * 
	 * @param event launches manager interface
	 * @throws IOException if scene fails to launch
	 */
	public void managerLaunch(ActionEvent event) throws IOException {
		dbConnection newConnection = new dbConnection(); 
        newConnection.launchScene("managerScene.fxml");
	}
	
	/**
	 * 
	 * @param event launches manager orders scene interface
	 * @throws IOException if scene fails to launch
	 */
	public void managerOrders(ActionEvent event) throws IOException {
		//System.out.println("Launched manager order page");
		dbConnection newConnection = new dbConnection(); 
        newConnection.launchScene("managerCreateOrder.fxml");
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
			stage.close();
		}
	}
	
	/**
	 * 
	 * @param event adds item it cashier interface for customer total
	 * @throws IOException if scene fails to launches
	 */
	public void addItem (ActionEvent event) throws IOException {
		Connection conn = null;
        string dbConnectionString = dbConnection.connectToDatabase();
        
        String productID = addItemField.getText();
        productIDs.addElement(productID);
        Double weight = Double.parseDouble( weightField.getText() );
        weights.addElement(weight);
        String sql = "SELECT productName, sellPrice FROM products WHERE productID = " + productID;
        
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
            sql = "UPDATE products SET currentStock = currentStock - " + weight + " WHERE productID = " + productID;
            statement.execute(sql);
            
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
        String dbConnectionString = dbConnection.connectToDatabase();
        
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
	}
	
	/**
	 * 
	 * @param event adds an item to order list in managers order page interface
	 * @throws IOException if scene fails to launch
	 */
	public void addOrderItem (ActionEvent event) throws IOException {
		//System.out.println("Launched manager order item button");
		Connection conn = null;
        String dbConnectionString = dbConnection.connectToDatabase();
        
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
        String dbConnectionString = dbConnection.connectToDatabase();
        
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
        runningOrderTotal.setText("$0");
        orderIDField.setText("");
        orderWeightField.setText("");
        distributorField.setText("");
	}
}// end SampleController class
