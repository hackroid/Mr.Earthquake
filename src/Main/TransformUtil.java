package Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import search.input;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chenmin on 2017/12/25.
 */
public class TransformUtil {
    /**
     * 
     * @param UTC_date_start
     * @param UTC_date_end
     * @param magS
     * @param magX
     * @param region
     * @return
     */
    public static ObservableList<Earthquake> SearchRequest(String UTC_date_start,
                                                           String UTC_date_end,
                                                           double magS,
                                                           double magX,
                                                           String region) {
        ObservableList<Earthquake> earthquakes = FXCollections.observableArrayList();
        input data = new input(UTC_date_start, UTC_date_end, magS, magX, region);
        ResultSet rs = data.search();

        try {
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

}
