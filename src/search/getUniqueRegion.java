package search;

import com.*;
import java.sql.*;

public class getUniqueRegion {
	public static ResultSet get() {
		ResultSet rs = null;
		Connection conn = mysqlJDBC.getConnection();
		Statement stmt = mysqlJDBC.getStatement(conn);
		String sql = "select count(*) as repetitions, region from quake group by region HAVING repetitions > 0";
		try {
			rs = stmt.executeQuery(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
