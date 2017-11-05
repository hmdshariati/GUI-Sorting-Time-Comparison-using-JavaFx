package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReadDataController{
	@FXML private Button btnAdd, btnFinish, btnAutomate;
	@FXML private TextField temperature;
	
	@FXML public void add(ActionEvent event){
		try {
			MainController.stmt.executeUpdate("Insert into temp_values(Temperature_Reading) values("+temperature.getText()+")");
		} catch (SQLException e) {
			System.err.println("ERROR INSERTING DATA");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("ADD Done");
	}
	
	@FXML public void finish(ActionEvent event){
		System.out.println("FINISH");
		try{
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			VBox root = (VBox)loader.load(getClass().getResource("main.fxml").openStream());
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("ASSIGNMENT");
			primaryStage.show();
		}catch(IOException e){
			System.out.println("IO Exception loading main.fxml");
			e.printStackTrace();
		}
	}
	
	@FXML public void automate(ActionEvent event){
		System.out.println("AUTOMATE");
		try{
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			VBox root = (VBox)loader.load(getClass().getResource("automate.fxml").openStream());
			Scene scene = new Scene(root,200,150);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("AUTOMATE");
			primaryStage.show();
		}catch(IOException e){
			System.out.println("IO Exception loading automate.fxml");
			e.printStackTrace();
		}
	}
}
