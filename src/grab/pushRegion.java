package grab;

import java.sql.*;
import com.*;

import search.getUniqueRegion;

public class pushRegion {
	public static void push() {
		
		try {
			ResultSet rs = null;
			Connection conn = mysqlJDBC.getConnection();
			Statement stmt = mysqlJDBC.getStatement(conn);
			rs = getUniqueRegion.get();
			while(rs.next()) {
				String sql = "insert into region (region) values (\'" + rs.getString(2) + "\')";
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
