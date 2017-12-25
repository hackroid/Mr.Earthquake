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
    private String mapimg;
    /*
    * list of earthquake
    */
    public void setMap(String map){
        mapimg = map;
        Image image= new Image(mapimg);
        imageView = new ImageView();
        imageView.setImage(image);
        group=new Group();
        group.getChildren().add(imageView);
    }
    public Node getGroup(){
        return group;
    }
    public void setEQ(ObservableList<Earthquake> eq){
        removeEQ();
        MapUtil mu=new MapUtil();
        for(int i=0;i<eq.size();i++){
            Node temp=mu.showEQ(eq.get(i));
            group.getChildren().add(temp);
        }
    }
    public void removeEQ(){
        setMap(mapimg);
    }
}
