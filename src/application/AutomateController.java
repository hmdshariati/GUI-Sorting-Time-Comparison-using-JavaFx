package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AutomateController {
	@FXML private Button btnGenerate;
	@FXML private TextField noOfRandomEntries;
	
	@FXML public void generate(ActionEvent event) {
		int n = Integer.parseInt(noOfRandomEntries.getText());
		Random rand = new Random();
		
		try {
			while(n-- > 0) {
				MainController.stmt.executeUpdate("Insert into temp_values(Temperature_Reading) values("+rand.nextInt(10000)+")");
			}
		} catch (SQLException e) {
			System.err.println("ERROR INSERTING RANDOM DATA");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Added random data");
		
		try{
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			VBox root = (VBox)loader.load(getClass().getResource("readData.fxml").openStream());
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("READ DATA");
			primaryStage.show();
		}catch(IOException e){
			System.out.println("IO Exception in loading readData.fxml");
			e.printStackTrace();
		}
	}
}
