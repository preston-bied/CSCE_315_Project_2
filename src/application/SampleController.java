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
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class SampleController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML private Button cashierButton;
	@FXML private Button managerButton;
	ArrayList<Button> buttons;
	
	public void cashierLaunch(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("cashierScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
	
	public void managerLaunch(ActionEvent e) {
		managerButton.setDisable(false);
		managerButton.setText(" ");
	}
	
}// end SampleController class
