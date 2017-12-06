package tempUI;
	
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {
    //    private final String cssFile = Main.class.getClassLoader()
    //                                    .getResource("gui.css")
    //                                    .toString();
    
    private final String dataFile = Main.class
    .getClassLoader()
    .getResource("earthquakes.csv")
    .toString().replace("file:","");
    
    
    private static String[] header;
    
    private static ObservableList<Earthquake> earthquakes = FXCollections.observableArrayList();
    
    private static TableView<Earthquake> tv = new TableView<Earthquake>();
  
    static void loadData(String file){
        try(BufferedReader reader
            = Files.newBufferedReader(Paths.get(file))){
            String  line = null;
            header = reader.readLine().split(",");
            String[] fields;
            while((line = reader.readLine())!=null){
                fields = line.split(",");
                
                earthquakes.add(new Earthquake(fields[0],fields[1].replace("\"",""),
                                               Double.parseDouble(fields[2]),Double.parseDouble(fields[3]),
                                               Double.parseDouble(fields[4]),Double.parseDouble(fields[5]),fields[6]));
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        } catch (NumberFormatException e){
            System.err.format("NumberFormatException: %s%n", e);
        }
    }
    
    @Override
    public void init(){
        loadData(dataFile);
    }
    
    private void setItems(TableView<Earthquake> tv){
        tv.setItems(earthquakes);
    }
    
    public static void main(String[] args) {
               launch(args);
    }
    
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Display EarthQuake Data");
        Group root = new Group();
        Scene scene = new Scene(root);
        //        scene.getStylesheets().add(cssFile);
        Pane pane = new Pane();
        VBox vBox = new VBox();
        root.getChildren().add(pane);
        pane.getChildren().add(vBox);
        
        TableView<Earthquake> tv = new TableView<Earthquake>();
        
        tv.setPrefWidth(950);
        tv.setPrefHeight(600);
        
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> loadData(dataFile));
        
        vBox.getChildren().add(hBox);
        
        // Add the TableView to the box
        vBox.getChildren().add(tv);
        
        TableColumn<Earthquake,String> c1 = new TableColumn<Earthquake, String>("id");
        c1.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getId()));
        
        TableColumn<Earthquake,String> c2 = new TableColumn<Earthquake, String>("UTC_date");
        c2.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getUTC_date()));
        
        tv.getColumns().addAll(c1,c2);
        
        hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        
        setItems(tv);
        stage.setScene(scene);
        stage.show();
    }
}
