package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
	@FXML private AnchorPane scenePane;
	@FXML private Label showChicken = new Label();
	@FXML private TableView<productDescription> tableDescription;
	@FXML private TableColumn<productDescription, String> col_name;
	@FXML private TableColumn<productDescription, String> col_price;
	ArrayList<Button> buttons;
	
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
        
        String sql = "SELECT productName, sellPrice FROM products WHERE productName LIKE 'Chicken%' OR productName LIKE '%Chicken'";
        
        // connect to database
        ObservableList<productDescription> list = FXCollections.observableArrayList();
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
            
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);
            String product = " ";
            String price = " ";
            while (queryOutput.next()) {
            	String productName = queryOutput.getString("productName");
            	String sellPrice = queryOutput.getString("sellPrice");
            	product += productName + "\n";
            	price += sellPrice + "\n";
;            	 //System.out.println(queryOutput.getString("productName") + " " + queryOutput.getString("sellPrice"));
            	//productDescription currProduct = new productDescription(productName, sellPrice);
            	// list.add(new productDescription( queryOutput.getString("productName"), queryOutput.getString("sellPrice") ) );
            	//System.out.println(currProduct.getProductName());
            	//list.add(currProduct);
            }
            showChicken.setText(product);
//            col_name.setCellValueFactory(new PropertyValueFactory<productDescription,String> (currProduct.getProductName()));
//            col_price.setCellValueFactory(new PropertyValueFactory<productDescription,String>(currProduct.getSalePrice()));

           // tableDescription.setItems(list);
           
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
	}	
	
	//button to generate chicken products in cashier interface
	public void cashierBeefQuery(ActionEvent event) throws IOException {

	}	
	//button to generate chicken products in cashier interface
	public void cashierCLamnbQuery(ActionEvent event) throws IOException {

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
	
//	public void initialize (URL url, ResourceBundle rb) {
//		
//		col_name.setCellValueFactory(new PropertyValueFactory<productDescription, String>("name"));
//		col_price.setCellValueFactory(new PropertyValueFactory<productDescription, String>("sellPrice"));
//		tableDescription.setItems(list));
//	}
	
}// end SampleController class
