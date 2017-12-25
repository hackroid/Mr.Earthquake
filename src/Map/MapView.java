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
    private Group EQP; //earthquake point

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
        Node temp = mu.showEQ(eq);
        group.getChildren().add(temp);
    }
    public void addEarthquake(ObservableList<Earthquake> eq){
        MapUtil mu=new MapUtil();
        for(int i=0;i<eq.size();i++){
            Node temp=mu.showEQ(eq.get(i));
            EQP.getChildren().add(temp);
        }
        group.getChildren().add(EQP);
    }
    public void addEarthquack(ObservableList<Earthquake> eql){

    }
    public void removeEQ(){
        group.getChildren().remove(EQP);
    }
}
