package application;	
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * 
 * @author Robert O'Reilly
 * @author Preston Bieh
 * @author Ethan Martinez
 * @author Cyrus Buhariwala
 * Method receive a launch command from main and passes in "arg"
 * This methods launches the application window and links to the sampleController.java
 * 
 * @param primaryStage creates the primary scene window for the application
 * @throws Exception if scene application fails to launch
 */
public class Main extends Application {
	@Override
	public void start( Stage primaryStage ) throws Exception {
		try {
			//create root stage and scene			
			Parent root = FXMLLoader.load(getClass().getResource("homePageGUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			//running stage
			primaryStage.show();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("GUI");	
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
        // build connection with credentials
        Connection conn = null;
        String teamNumber = "2";
        String sectionNumber = "950";
        String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
        dbSetup myCredentials = new dbSetup(); 
        
        // connect to database
        try {
            conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");       
        //JOptionPane.showMessageDialog(null,"Opened database successfully");
        
        //launch the application
		launch(args);
		
	      //closing the connection
	      try {
	        conn.close();
	        JOptionPane.showMessageDialog(null,"Connection Closed.");
	      } catch(Exception e) {
	        JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
	      }
	}
}
