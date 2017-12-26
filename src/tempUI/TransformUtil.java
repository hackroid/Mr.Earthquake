package tempUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import search.input;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chenmin on 2017/12/25.
 * have not been tested
 */
public class TransformUtil {
    public static ObservableList<Earthquake> SearchRequest(String UTC_date_start,
                                                           String UTC_date_end,
                                                           double magS,
                                                           double magX,
                                                           String region) {
        ObservableList<Earthquake> earthquakes = FXCollections.observableArrayList();
        input data = new input(UTC_date_start, UTC_date_end, magS, magX, region);
        ResultSet rs = data.search();

        try {
            System.out.println(rs.getFetchSize());
            while (rs.next()) {
                earthquakes.add(new Earthquake(rs.getString(1),
                        rs.getString(2).replace("\"","")+"  "+rs.getString(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(7),
                        rs.getDouble(6),
                        rs.getString(8)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return earthquakes;
    }
//      list all region
//    public static ObservableList<Earthquake> RegionList(ResultSet rs) {
//        ObservableList<Earthquake> earthquakes = FXCollections.observableArrayList();
//        try {
//            while (rs.next()) {
//                earthquakes.add(new Earthquake(rs.getString(0),
//                        rs.getString(1).replace("\"",""),
//                        rs.getDouble(2),
//                        rs.getDouble(3),
//                        rs.getDouble(4),
//                        rs.getDouble(5),
//                        ""));
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return earthquakes;
//    }
}
