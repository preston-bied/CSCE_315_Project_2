package application;	
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			//create root stage and scene
			
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			BorderPane root2 = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));			
			Scene cashierScene = new Scene(root2);
			cashierScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//running stage 
			primaryStage.setScene(scene);
			primaryStage.setTitle("User Select");
			primaryStage.show();	
			
			button.setOnAction(e->{
				
			}
			
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
        } catch (Exception e) {
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
