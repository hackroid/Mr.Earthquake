package Map;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tempUI.Earthquake;

/**
 * Created by chenmin on 2017/12/16.
 */
public class MapUtil {
    private double mapWidth=900,mapHigth=600;
    public void setMapWidth(double mapWidth) {
        this.mapWidth = mapWidth;
    }
    public void setMapHigth(double mapHigth) {
        this.mapHigth = mapHigth;
    }

    /**
     *
     * @param eq
     * @return
     */
    public Node drawEarthQuack(Earthquake eq) {
        Circle circle = new Circle();
        circle.setCenterX(convert(1,eq.getLatiude()));
        circle.setCenterY(convert(2,eq.getLongitude()));
        circle.setFill(Color.RED);
        circle.setRadius(3.0f);
        return circle;
    }
    /**
     *
     * @param mark tell the function the value is longitude or latiude.\n1:latiude.\n2:longitude.
     * @param value
     * @return coord in map.
     */
    public double convert(int mark,double value){
        if(mark==1){
            double res = (mapWidth/360)*(value+180);
            return res;
        }else if(mark==2){
            double res = (mapHigth/180)*(value+90);
            return res;
        }
        return -1;
    }
}
