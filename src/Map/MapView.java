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
import javafx.scene.layout.StackPane;
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

    /**
     *
     * @param map map image uri
     */
    public void setMap(String map){
        mapimg = map;
        Image image= new Image(mapimg);
        imageView = new ImageView();
        imageView.setImage(image);
        group=new Group();
        group.getChildren().add(imageView);
    }

    /**
     * return node object for insert to table.
     * @return
     */
    public Node getGroup(){
        return group;
    }

    /**
     * using MapUtil to draw circle list.
     * @param eq observablelist of type Earthquake.
     */
    public void setEQ(ObservableList<Earthquake> eq){
        removeEQ();
        MapUtil mu=new MapUtil();
        for(int i=0;i<eq.size();i++){
            if((eq.get(i).getLatiude() < 82.00) && (eq.get(i).getLatiude() > -82.00)) {
                Node temp = mu.showEQ(eq.get(i));
                group.getChildren().add(temp);
            }
        }
    }

    /**
     * clear map points, for next add.
     */
    public void removeEQ(){
        setMap(mapimg);
    }
}
