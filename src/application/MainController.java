package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController implements Initializable{
	@FXML private Button btnReadData, btnQuickSort, btnRadixSort, btnHeapSort, btnViewGraph;
	@FXML private ChoiceBox<Integer> base = new ChoiceBox<>();
	@FXML private ChoiceBox<String> pivotSelect = new ChoiceBox<>();
	boolean quickSortDone = false, radixSortDone = false, heapSortDone = false;
	static Connection myConn;
	static Statement stmt;
	int[] values;
	int[] noOfTempReadings;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gui_assignment?autoReconnect=true&useSSL=false", "root", "root");
			stmt = myConn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select Temperature_Reading from temp_values");
			values = new int[rs.last() ? rs.getRow() : 0];
			
			int[] temp = {10000,100000,500000};
			noOfTempReadings = temp;
			
			rs.setFetchDirection(ResultSet.FETCH_REVERSE);
			rs.next();
			int i = values.length-1;
			while (rs.previous()) {
				values[i--] = rs.getInt("Temperature_Reading");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("DATABASE CONNECT ERROR");
			System.exit(1);
		}
		
		System.out.println("No. of entries in database = "+values.length);
		
		ObservableList<String> pivotValues = FXCollections.observableArrayList("n/2","n/4","n/6");
		pivotSelect.setItems(pivotValues);
		pivotSelect.setValue("n/2");
		pivotSelect.setDisable(true);
		
		ObservableList<Integer> baseValues = FXCollections.observableArrayList(2,10,16);
		base.setItems(baseValues);
		base.setValue(10);
		base.setDisable(true);
		
		btnViewGraph.setDisable(true);
	}
	
	@FXML public void readData(ActionEvent event){
		System.out.println("READ DATA");
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
			System.out.println("IO Exception in read data");
			e.printStackTrace();
		}
	}
	
	@FXML public void quickSort(ActionEvent event){
		if(quickSortDone)	return;
		System.out.println("QUICK SORT");
		pivotSelect.setDisable(false);
		
		quickSortDone = true;
		if(quickSortDone && radixSortDone && heapSortDone)	btnViewGraph.setDisable(false);
	}
	
	@FXML public void radixSort(ActionEvent event){
		if(radixSortDone)	return;
		System.out.println("RADIX SORT");
		base.setDisable(false);
		
		radixSortDone = true;
		if(quickSortDone && radixSortDone && heapSortDone)	btnViewGraph.setDisable(false);
	}
	
	@FXML public void heapSort(ActionEvent event){
		if(heapSortDone)	return;
		System.out.println("HEAP SORT");
		
		heapSortDone = true;
		if(quickSortDone && radixSortDone && heapSortDone)	btnViewGraph.setDisable(false);
	}
	
	@SuppressWarnings("unchecked")
	@FXML public void viewGraph(ActionEvent event){
		System.out.println("VIEW GRAPH");
		
		Stage stage = new Stage();
		stage.setTitle("VIEW GRAPH");
		
		CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("Time Complexity Analsis");
        xAxis.setLabel("No. of temperature readings");  
        yAxis.setLabel("Time(ms)");        
 
        XYChart.Series<String, Number> qs = new Series<>();
        qs.setName("Quick Sort");   
        XYChart.Series<String, Number> rs = new XYChart.Series<>();
        rs.setName("Radix Sort");
        XYChart.Series<String, Number> hs = new XYChart.Series<>();
        hs.setName("Heap Sort");   
        
        Random rand = new Random();
        for(int t : noOfTempReadings) {
        	long start, end;
        	int[] a = new int[t], b = new int[t], c = new int[t];
        	for(int i = 0; i < t; i++) {
        		int r = rand.nextInt(values.length);
        		a[i] = values[r];
        		b[i] = values[r];
        		c[i] = values[r];
        	}
        	
        	start = System.currentTimeMillis();
        	String pivotValue = pivotSelect.getValue();
        	int pivotPartition = 2;
        	if(pivotValue.equals("n/4"))	pivotPartition = 4;
        	else if(pivotValue.equals("n/6"))	pivotPartition = 6;
        	QuickSort.qSort(a, 0, t-1, pivotPartition);
        	end = System.currentTimeMillis();
        	qs.getData().add(new XYChart.Data<String, Number>(Integer.toString(t), end-start));  
        	
        	start = System.currentTimeMillis();
        	RadixSort.rSort(b, base.getValue());
        	end = System.currentTimeMillis();
        	rs.getData().add(new XYChart.Data<String, Number>(Integer.toString(t), end-start));  
        	
        	start = System.currentTimeMillis();
        	HeapSort.heapSort(c);
        	end = System.currentTimeMillis();
        	hs.getData().add(new XYChart.Data<String, Number>(Integer.toString(t), end-start));  
        }
  
        bc.getData().addAll(qs,rs,hs);
        
        Scene scene  = new Scene(bc,800,600);
        stage.setScene(scene);
        stage.show();
	}
}
