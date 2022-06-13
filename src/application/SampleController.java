package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SampleController {

	private Button cashierButton;
	private Button managerButton;
	ArrayList<Button> buttons;
	// top row

	// reset all
	public void cashierLaunch(ActionEvent e) {
		cashierButton.setDisable(false);
		cashierButton.setText(" ");
	}
	public void managerLaunch(ActionEvent e) {
		managerButton.setDisable(false);
		managerButton.setText(" ");
	}
}// end SampleController class
