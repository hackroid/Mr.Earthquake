package search;

import com.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * get unique region list.
 * transform result set to observablelist<String>
 */
public class getUniqueRegion {
	/**
	 * To combox list.
	 * @return list of region, to combox list.
	 */
	public static ObservableList<String> getRegions() {
		ObservableList<String> regions = FXCollections.observableArrayList();
		ResultSet rs = null;
		String re = "";
		Connection conn = mysqlJDBC.getConnection();
		Statement stmt = mysqlJDBC.getStatement(conn);
		String sql = "select count(*) as repetitions, region from region group by region HAVING repetitions > 0";
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				re = rs.getString(2);
				regions.add(re);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return regions;
	}
}
