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
	@FXML private Button logoutButton;
	@FXML private Button addItemButton;
	@FXML private AnchorPane scenePane;
	@FXML private Label showProductName = new Label();
	@FXML private Label showProductPrice = new Label();
	@FXML private Label showProductID = new Label();
	@FXML private Label saleItems = new Label();
	@FXML private Label runningTotal = new Label();
	@FXML private TextField addItemField = new TextField();
	@FXML private TextField weightField = new TextField();
	@FXML private TableView<productDescription> tableDescription;
	@FXML private TableColumn<productDescription, String> col_name;
	@FXML private TableColumn<productDescription, String> col_price;
	ArrayList<Button> buttons;
	
	public String sale = "";
	public Double saleTotal = 0.0;
	public int currSaleInvoiceID = 0;
	public int currLineItemID = 0;
	public int employeeID = 1;
	public String currDate = "2022-06-14";
	public Vector<String> productIDs = new Vector<>();
	public Vector<Integer> lineItemIDs = new Vector<>();
	public Vector<Double> weights = new Vector<>();
	
	//button to launch cashier interface
	public void cashierLaunch(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("cashierScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	//button to generate chicken products in cashier interface
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
	
	//button to generate chicken products in cashier interface
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
	//button to generate chicken products in cashier interface
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
	
	//button to launch manager interface
	public void managerLaunch(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("managerScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	//logout notification
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
	
	//add item to total 
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
            df.format(totalPrice);
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
        runningTotal.setText("0");
        addItemField.setText("");
        weightField.setText("");
	}
	
	
}// end SampleController class
