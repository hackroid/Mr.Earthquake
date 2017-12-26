package tempUI;
	
import Map.MapView;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
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
    public static String WorldWide= "------------WORLD WIDE------------";
    private Label res_size = new Label();
//for gridpane
    private ComboBox<String> cbox1 = new ComboBox<String>();
    private ComboBox<Float> cbox2 = new ComboBox<Float>();
    private ComboBox<String> cbox3 = new ComboBox<String>();
//for chart1
    private static ObservableList<XYChart.Data<String,Number>> data =
            FXCollections.observableArrayList();
    private Button saveButton = new Button("Save Chart");
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String,Number> bc =
            new BarChart<String,Number>(xAxis,yAxis);
    private String[] magRange = {"<=3.0", "3.0 to 4.0", "4.0 to 5.0", "5.0 to 6.0", "6.0 to 7.0",">=7.0"};
    private VBox  chartBox = new VBox();
    private XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();

    /**
     *
     */
    @Override
    public void init(){
        Locale.setDefault(Locale.US);
        fromDate = new DatePicker();
        toDate = new DatePicker();
        toDate.setValue(LocalDate.now());
        fromDate.setValue(toDate.getValue().minusMonths(1));
        fromDate.setEditable(false);
        toDate.setEditable(false);
        regions=search.getUniqueRegion.getRegions();
        cbox3.setId("cbx-reg");
        cbox3.setItems(regions);
        cbox3.setPromptText(WorldWide);
        cbox3.setValue(WorldWide);
        cbox3.setVisibleRowCount(20);
        AutoCompleteComboBoxListener<String> autoBox = new AutoCompleteComboBoxListener(cbox3);
        bc.getData().add(series);
        bc.setPrefWidth(400);
        bc.setPrefHeight(400);
        chartBox.setPadding(new Insets(10));
        chartBox.setAlignment(Pos.CENTER);
        chartBox.getChildren().add(bc);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(saveButton);
        chartBox.getChildren().add(hbox);
    }

    /**
     *
     * @param tv
     */
    private void refreshItems(TableView<Earthquake> tv) {
        if(cbox3.getValue()==null||cbox3.getValue().length()==0) {cbox3.setValue(WorldWide);}
        earthquakes = TransformUtil.SearchRequest(fromDate.getValue().toString(),
                toDate.getValue().toString(), Double.parseDouble(cbox1.getValue().toString()),
                Double.parseDouble(cbox2.getValue().toString()), cbox3.getValue().toString());
        res_size.setText(earthquakes.size()+" earthquakes found.");
        System.out.println("1");
        System.out.println(earthquakes.size());
        tv.setItems(earthquakes);
    }

    /**
     *
     */
    public void refreshChart(){
        data.clear();
        double mag;
        bc.setTitle("Chart by Magnitude Range");
        xAxis.setLabel("Magnitude");
        yAxis.setLabel("Number of Earthquakes");
        bc.setLegendVisible(false);
        bc.setAnimated(false);
        int count[]=new int[6];
        Iterator it = earthquakes.iterator();
        while(it.hasNext()) {
            Earthquake e = (Earthquake) it.next();
            mag=e.getMagnitude();
            if (mag <= 3.0) { ++count[0]; }
            else if (mag < 4.0) { ++count[1]; }
            else if (mag < 5.0) { ++count[2]; }
            else if (mag < 6.0) { ++count[3]; }
            else if(mag < 7.0){ ++count[4]; }
            else{ ++count[5]; }
        }
        for(int i=0;i<6; ++i) {
            data.add(new XYChart.Data<String, Number>(magRange[i], count[i]));
        }
        series.setData(data);
        chartBox.setAlignment(Pos.CENTER);
    }

    /**
     *
     */
    public void setGridPane(){
        final Label lb_from = new Label(" From: ");
        final Label lb_to= new Label(" To: ");
        final Label lb_mag = new Label(" Magnitude: ");
        final Label lb_mag_to = new Label("  ~   ");
        final Label lb_region = new Label(" Region: ");
        final Button search_btn = new Button("Search");
        final Button update_btn = new Button("Update");
        HBox hBox1= new HBox();
        HBox hBox2= new HBox();
        HBox hBox3= new HBox();
        HBox hBox4= new HBox();
        hBox1.getChildren().addAll(lb_from,fromDate,lb_to,toDate);
        hBox2.getChildren().addAll(lb_mag,cbox1,lb_mag_to,cbox2);
        hBox3.getChildren().addAll(lb_region,cbox3);
        hBox4.getChildren().addAll(res_size,update_btn,search_btn);
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
        search_btn.getStyleClass().add("button");
        update_btn.getStyleClass().add("button");
        hBox1.getStyleClass().add("hbox");
        hBox2.getStyleClass().add("hbox");
        hBox3.getStyleClass().add("hbox");
        hBox4.getStyleClass().add("hbox4");
        hBox4.setSpacing(40);
        grid.getStyleClass().add("grid");
        grid.add(hBox1,1,0,5,1);
        grid.add(hBox2,1,1,4,1);
        grid.add(hBox3,1,2,4,1);
        grid.add(hBox4,2,3,4,1);
        res_size.setText(earthquakes.size()+" earthquakes found.");
//        grid.setGridLinesVisible(true);

        search_btn.setOnAction(event -> {
                    refreshItems(tv);
                    mc.setEQ(earthquakes); tab2.setContent(mc.getGroup());
                    refreshChart(); tab3.setContent(chartBox);
                }
        );
//------------------------------------------------------------------------
        update_btn.setOnAction(event -> {

        });
    }

    /**
     *
     */
    public void setTable(){
        TableColumn<Earthquake,String> c1 = new TableColumn<Earthquake, String>("Id");
        c1.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getId()));
        tv.getColumns().add(c1);
        TableColumn<Earthquake,String> c2 = new TableColumn<Earthquake, String>("UTC_date");
        c2.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getUTC_date()));
        tv.getColumns().add(c2);
        TableColumn<Earthquake, Number> c3 = new TableColumn<Earthquake, Number>("Latitude");
        c3.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getLatiude()));
        tv.getColumns().add(c3);
        TableColumn<Earthquake,Number> c4 = new TableColumn<Earthquake, Number>("Longitude");
        c4.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getLongitude()));
        tv.getColumns().add(c4);
        TableColumn<Earthquake,Number> c5 = new TableColumn<Earthquake, Number>("Depth");
        c5.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getDepth()));
        tv.getColumns().add(c5);
        TableColumn<Earthquake,Number> c6 = new TableColumn<Earthquake, Number>("Magnitude");
        c6.setCellValueFactory(e -> new ReadOnlyDoubleWrapper(e.getValue().getMagnitude()));
        tv.getColumns().add(c6);
        TableColumn<Earthquake,String> c7 = new TableColumn<Earthquake, String>("Region");
        c7.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getRegion()));
        tv.getColumns().add(c7);
        refreshItems(tv);
        tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        MenuItem item = new MenuItem("Copy");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList rowList = (ObservableList) tv.getSelectionModel().getSelectedItems();
                StringBuilder clipboardString = new StringBuilder();
                for (Iterator it = rowList.iterator(); it.hasNext();) {
                    Earthquake row = (Earthquake)it.next();
                    String cell=""; cell=row.toString();
                    clipboardString.append(cell);
                    clipboardString.append('\n');
                }
                final ClipboardContent content = new ClipboardContent();
                content.putString(clipboardString.toString());
                Clipboard.getSystemClipboard().setContent(content);
            }
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(item);
        tv.setContextMenu(menu);
    }

    /**
     * 
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Display EarthQuakes");
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssFile);
        VBox vBox = new VBox();
        setGridPane();//and grid pane
//        vBox.setSpacing(6);
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
        setTable();

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
        refreshChart();
        saveButton.getStyleClass().add("button");
        saveButton.setOnAction((e)->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Chart");
            fileChooser.setInitialFileName("barchart.png");
            File selectedFile = fileChooser.showSaveDialog(stage);
            if (selectedFile != null) {
                try {
                    WritableImage snap = bc.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(snap, null),
                            "png", selectedFile);
                } catch (IOException exc) {
                    System.err.println(exc.getMessage());
                }
            }
        });
        /**
         *
         */
        tab3.setContent(chartBox);
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
