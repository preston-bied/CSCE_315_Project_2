package application;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class SampleController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML private Button cashierButton;
	@FXML private Button managerButton;
	@FXML private Button logoutButton;
	@FXML private AnchorPane scenePane;
	ArrayList<Button> buttons;
	
	public void cashierLaunch(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("cashierScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	public void managerLaunch(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("managerScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	public void logout( ActionEvent event ) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout");
		alert.setContentText("Do you wish to continue?");		
		
		if( alert.showAndWait().get() == ButtonType.OK) {
			Parent root = FXMLLoader.load(getClass().getResource("homePageGUI.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			System.out.println("You have successfully logged out");
			stage.close();
		}
	}
	
}// end SampleController class
