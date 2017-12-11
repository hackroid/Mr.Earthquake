package tempUI;
	
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
    
    private static ObservableList<Earthquake> earthquakes = FXCollections.observableArrayList();
    private static TableView<Earthquake> tv = new TableView<Earthquake>();
    private static GridPane grid = new GridPane();
    
    private DatePicker fromDate = null;
    private DatePicker toDate = null;

    /**Need to extract the min&max date to set default date
     private String     minDate = null;
     private String     maxDate = null;
     **/
    
    //read data from .csv file
    static void loadData(String file){
        try(BufferedReader reader
            = Files.newBufferedReader(Paths.get(file))){
            String  line = null;
            line = reader.readLine();//ignore header
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
    
    public static void start(String[] args) {
               launch(args);
    }
    
    public void setGridPane(){
        final Label lb_from = new Label("From: ");
        final Label lb_to= new Label("To: ");
        final Button search_btn = new Button("Search");
        final Label lb_mag = new Label("Magnitude:      ");
        final Label lb_mag_to = new Label("----------------");
        final Label lb_region = new Label("Region: ");
        fromDate = new DatePicker();
        //        fromDate.setValue(minDate);
        toDate = new DatePicker();
        //        toDate.setValue(maxDate);
        
        ComboBox<String> cbox1 = new ComboBox<String>();
        ComboBox<Float> cbox2 = new ComboBox<Float>();
        ComboBox<String> cbox3 = new ComboBox<String>();
        
        //for cbox1
        ObservableList<String> mag_range
        = FXCollections.observableArrayList("0.0","1.0","2.0","3.0"
                                            ,"4.0","5.0","6.0","7.0","8.0");
        cbox1.setItems(mag_range);
        cbox1.setVisibleRowCount(5);
        cbox1.setValue("0.0");
        
        //for cbox2
        cbox2.setVisibleRowCount(5);
        cbox2.setValue(new Float(9.0f));
        ObservableList<Float> mag_range_max = FXCollections.observableArrayList();
        for (float i=1.0f; i<=10.0; i=i+1.0f){
            mag_range_max.addAll(new Float(i));
        }
        cbox2.setItems(mag_range_max);
        
        //changelistener for cbox1, making sure values in cbox2 larger than cbox1
        cbox1.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
            for (float i=1.0f;i<=10.0;i=i+1.0f){
                cbox2.getItems().remove(i);
            }
            for(float i=Float.parseFloat(t1)+1.0f;i<=10.0;i=i+1.0f) {
                cbox2.getItems().add(i);
            }
        });
        
        /** ------------for cbox3-------------
         * need a method to extract all regions from the data
         * then set string items to cbox3
         cbox3.setVisibleRowCount(5);
         cbox3.setItems(Ov regions)
         -------------------------------------**/
        cbox3.setValue("-------WORLD WILD-------");
        
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        grid.getColumnConstraints().addAll(new ColumnConstraints(100),new ColumnConstraints(150)
                                           , new ColumnConstraints(150),new ColumnConstraints(100),new ColumnConstraints(150));
        
        grid.add(lb_from,0,0);
        grid.add(fromDate,1,0);
        grid.add(lb_to,3,0);
        grid.add(toDate,4,0);
        grid.add(search_btn,10,10);
        grid.add(lb_mag,0,2);
        grid.add(cbox1,1,2);
        grid.add(lb_mag_to,2,2);
        grid.add(cbox2,3,2);
        grid.add(lb_region,0,4);
        grid.add(cbox3,1,4,2,1);
        
        /**Method: searQuakes
         search_btn.setOnAction(event ->
         searchQuakes(fromDate.getValue().toString(),toDate.getValue().toString(),cbox1.getValue.toString()
         ,cbox2.getValue.toString(),cbox3.getValue.toString());
         );
         **/
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Display EarthQuakes");
        Group root = new Group();
        Scene scene = new Scene(root);
        //        scene.getStylesheets().add(cssFile);
        VBox vBox = new VBox();
        vBox.setSpacing(6);
        vBox.setPadding(new Insets(12));
        vBox.setAlignment(Pos.CENTER);
        setGridPane();
        
        root.getChildren().add(vBox);
        vBox.getChildren().add(grid);
        
        tv.setPrefWidth(800);
        tv.setPrefHeight(500);
        
        TableColumn<Earthquake,String> c1 = new TableColumn<Earthquake, String>("id");
        c1.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getId()));
        TableColumn<Earthquake,String> c2 = new TableColumn<Earthquake, String>("UTC_date");
        c2.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getUTC_date()));
        TableColumn<Earthquake, Number> c3 = new TableColumn<Earthquake, Number>("latiude");
        c3.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getLatiude()));
        TableColumn<Earthquake,Number> c4 = new TableColumn<Earthquake, Number>("longitude");
        c4.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getLongitude()));
        TableColumn<Earthquake,Number> c5 = new TableColumn<Earthquake, Number>("depth");
        c5.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getDepth()));
        TableColumn<Earthquake,Number> c6 = new TableColumn<Earthquake, Number>("magnitude");
        c6.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getMagnitude()));
        TableColumn<Earthquake,String> c7 = new TableColumn<Earthquake, String>("region");
        c7.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getRegion()));
        tv.getColumns().addAll(c1,c2,c3,c4,c5,c6,c7);
        setItems(tv);
        
        //create a tabpane
        TabPane tabpane = new TabPane();
        vBox.getChildren().add(tabpane);
        Tab tab1,tab2,tab3,tab4;
        //tab1: tableview
        tab1 = new Tab();
        tab1.setText("Table View");
        tab1.setContent(tv);
        tab1.setClosable(false);
        //tab2: map
        tab2 = new Tab();
        tab2.setText("World Map");
        tab2.setClosable(false);
        //tab3: chart
        tab3 = new Tab();
        tab3.setText("Chart");
        tab3.setClosable(false);
        //tab4: anything else?
        tab4 = new Tab();
        tab4.setText("Reserved");
        tab4.setClosable(false);
        //set default tab: tab1 - table view
        SingleSelectionModel<Tab> selectionModel = tabpane.getSelectionModel();
        selectionModel.select(tab1);
        
        tabpane.getTabs().addAll(tab1,tab2,tab3,tab4);
        
        stage.setScene(scene);
        stage.show();
    }
}
