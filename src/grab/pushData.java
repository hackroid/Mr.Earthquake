//*********Data collection complete, please do not restart it***********

package grab;
import com.mysqlJDBC;
import java.sql.*;

public class pushData {
	
	public static void push(int id, String date, String time, float latitude, float longitude, int depth, float magnitude, String region, Statement stmt) {
		String sql = "insert into quake (id, date, time, latitude, longitude, magnitude, depth, region, areacode) "+
					"values ('" + id + "','" + date + "','" + time + "','" + latitude + "','" + longitude + "','" + magnitude + "','" + depth + "','" + region + "','00')";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
