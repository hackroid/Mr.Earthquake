package tempUI;
	
import Map.MapView;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import search.input;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;

public class UI extends Application {
    private final String cssFile = UI.class.getClassLoader()
                                        .getResource("tempUI/application.css")
                                        .toString();
    
    private static ObservableList<Earthquake> earthquakes = FXCollections.observableArrayList();
    private static TableView<Earthquake> tv = new TableView<Earthquake>();
    private static GridPane grid = new GridPane();
    private static ObservableList<String> regions = FXCollections.observableArrayList();
    private MapView mc;
    private Node MapContent;
    private TabPane tabpane;
    private Tab tab1,tab2,tab3,tab4;
    
    private DatePicker fromDate = null;
    private DatePicker toDate = null;
    private String WorldWide= "-------WORLD WIDE-------";
    private Label res_size = new Label();
    @Override
    public void init(){
        Locale.setDefault(Locale.US);
        fromDate = new DatePicker();
        toDate = new DatePicker();
        toDate.setValue(LocalDate.now());
        fromDate.setValue(toDate.getValue().minusWeeks(1));
        fromDate.setEditable(false);
        toDate.setEditable(false);

//        ResultSet region_rs = search.getUniqueRegion.get();
//        String re="";
//        try {
//            while (region_rs.next()){
//                re=region_rs.getString(0);
//                regions.add(re);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        loadData(minDate,maxDate,0.0,10.0,WorldWild);
     }

    private void setItems(TableView<Earthquake> tv){
        tv.setItems(earthquakes);
    }

    public void setGridPane(){
        final Label lb_from = new Label(" From: ");
        final Label lb_to= new Label(" To: ");
        final Button search_btn = new Button("Search");
        final Label lb_mag = new Label(" Magnitude: ");
        final Label lb_mag_to = new Label("  ~   ");
        final Label lb_region = new Label(" Region: ");
        search_btn.setId("search-button");

        HBox hBox1= new HBox();
        HBox hBox2= new HBox();
        HBox hBox3= new HBox();
        ComboBox<String> cbox1 = new ComboBox<String>();
        ComboBox<Float> cbox2 = new ComboBox<Float>();
        ComboBox<String> cbox3 = new ComboBox<String>();

        hBox1.getChildren().addAll(lb_from,fromDate,lb_to,toDate);
        hBox2.getChildren().addAll(lb_mag,cbox1,lb_mag_to,cbox2);
        hBox3.getChildren().addAll(lb_region,cbox3);

        //for cbox1
        ObservableList<String> mag_range
                = FXCollections.observableArrayList("0.0","1.0","2.0","3.0"
                ,"4.0","5.0","6.0","7.0","8.0");
        cbox1.setItems(mag_range);
        cbox1.setVisibleRowCount(7);
        cbox1.setValue("0.0");

        //for cbox2
        cbox2.setVisibleRowCount(7);
        cbox2.setValue(new Float(9.0f));
        ObservableList<Float> mag_range_max = FXCollections.observableArrayList();
        for (float i=1.0f; i<=9.0; i=i+1.0f){
            mag_range_max.addAll(new Float(i));
        }
        cbox2.setItems(mag_range_max);

        //changelistener for cbox1, making sure values in cbox2 larger than cbox1
        cbox1.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
            float tmp=cbox2.getValue();
            for (float i=1.0f;i<=9.0;i=i+1.0f){
                cbox2.getItems().remove(i);
            }
            for(float i=Float.parseFloat(t1)+1.0f;i<=9.0;i=i+1.0f) {
                cbox2.getItems().add(i);
            }
            if(tmp>Float.parseFloat(t1)) {
                cbox2.setValue(tmp);
            }
        });

        fromDate.setId("cbx-date");
        toDate.setId("cbx-date");
        cbox1.setId("cbx-mag");
        cbox2.setId("cbx-mag");
        cbox3.setId("cbx-reg");
        hBox1.getStyleClass().add("hbox");
        hBox2.getStyleClass().add("hbox");
        hBox3.getStyleClass().add("hbox");
        grid.getStyleClass().add("grid");


        /** ------------for cbox3-------------
         * need a method to extract all regions from the data
         * then set string items to cbox3
         cbox3.setVisibleRowCount(5);
         cbox3.setItems(Ov regions)
         -------------------------------------**/
        cbox3.setItems(regions);
        cbox3.setValue(WorldWide);

        grid.add(hBox1,1,0,5,1);
        grid.add(search_btn,5,3);
        grid.add(hBox2,1,1,4,1);
        grid.add(hBox3,1,2,4,1);
        res_size.setText(earthquakes.size()+" earthquakes found.");
        grid.add(res_size,3,3);
//        grid.setGridLinesVisible(true);

        search_btn.setOnAction(event ->
                {
                    earthquakes = TransformUtil.SearchRequest(fromDate.getValue().toString(),
                            toDate.getValue().toString(), Double.parseDouble(cbox1.getValue().toString()),
                            Double.parseDouble(cbox2.getValue().toString()), cbox3.getValue().toString());
                    res_size.setText(earthquakes.size()+" earthquakes found.");
                    System.out.println("1");
                    System.out.println(earthquakes.size());
                setItems(tv);
                mc.setEQ(earthquakes);
                tab2.setContent(mc.getGroup());
                }
        );

    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Display EarthQuakes");
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssFile);
        VBox vBox = new VBox();
        setGridPane();//and grid pane
        vBox.setSpacing(6);
//        vBox.setPadding(new Insets(12));
        HBox hbox=new HBox();
        HBox himg=new HBox();
        himg.setMinWidth(300);
        himg.setMinHeight(200);
        himg.getStyleClass().add("hbox-img");
        hbox.getChildren().addAll(grid,himg);

        vBox.getChildren().add(hbox);
        root.getChildren().add(vBox);
        tv.setPrefWidth(860);
        tv.setPrefHeight(500);

        hbox.getStyleClass().add("hbox");
        vBox.getStyleClass().add("vbox");

        TableColumn<Earthquake,String> c1 = new TableColumn<Earthquake, String>("id");
        c1.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getId()));
        tv.getColumns().add(c1);
        TableColumn<Earthquake,String> c2 = new TableColumn<Earthquake, String>("UTC_date");
        c2.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getUTC_date()));
        tv.getColumns().add(c2);
        TableColumn<Earthquake, Number> c3 = new TableColumn<Earthquake, Number>("latiude");
        c3.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getLatiude()));
        tv.getColumns().add(c3);
        TableColumn<Earthquake,Number> c4 = new TableColumn<Earthquake, Number>("longitude");
        c4.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getLongitude()));
        tv.getColumns().add(c4);
        TableColumn<Earthquake,Number> c5 = new TableColumn<Earthquake, Number>("depth");
        c5.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getDepth()));
        tv.getColumns().add(c5);
        TableColumn<Earthquake,Number> c6 = new TableColumn<Earthquake, Number>("magnitude");
        c6.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getMagnitude()));
        tv.getColumns().add(c6);
        TableColumn<Earthquake,String> c7 = new TableColumn<Earthquake, String>("region");
        c7.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getRegion()));
        tv.getColumns().add(c7);

        setItems(tv);

        //create a tabpane
        tabpane = new TabPane();
        vBox.getChildren().add(tabpane);

        //tab1: tableview
        tab1 = new Tab();
        tab1.setText("Table View");
        tab1.setContent(tv);
        tab1.setClosable(false);
        //tab2: map
        tab2 = new Tab();
        tab2.setText("World Map");
        tab2.setClosable(false);
        mc=new MapView();
        mc.setMap("/tempUI/Mercator.jpg");
        MapContent=mc.getGroup();
        tab2.setContent(MapContent);
        //tab3: chart
        tab3 = new Tab();
        tab3.setText("Chart1");
        tab3.setClosable(false);
        //tab4: anything else?
        tab4 = new Tab();
        tab4.setText("Chart2");
        tab4.setClosable(false);
        //set default tab: tab1 - table view
        SingleSelectionModel<Tab> selectionModel = tabpane.getSelectionModel();
        selectionModel.select(tab1);

        tabpane.getTabs().addAll(tab1,tab2,tab3,tab4);
        stage.setScene(scene);
        stage.show();
    }
}
