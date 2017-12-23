package Map;
/*
* usage:
* MapController mc=new MapController();
* mc.setMap("Mercator1.png");
* then add it to scene
*/
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tempUI.Earthquake;

public class MapView{
    /*
    private double mapHigth;
    private double mapWidth;
    */
    private ImageView imageView;
    private Group group;

    /*
    * list of earthquake
    */
    public Group setMap(String mapimg){
        Image image= new Image(mapimg);
        imageView = new ImageView();
        imageView.setImage(image);
        group=new Group();
        group.getChildren().add(imageView);
        return group;
    }
    public void addEarthquake(Earthquake eq){
        MapUtil mu=new MapUtil();
        Node temp = mu.drawEarthQuack(eq);
        group.getChildren().add(temp);
    }
    public void addEarthquack(ObservableList<Earthquake> eql){

    }
    public void removeEQ(){
        group.getChildren().clear();
        group.getChildren().add(imageView);
    }
}
