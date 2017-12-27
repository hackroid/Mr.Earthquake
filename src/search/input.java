package search;

import java.sql.*;
import com.mysqlJDBC;
import tempUI.UI;

public class input {
	private static String UTC_date_start;
	private static String UTC_date_end;
	private static double magS;
	private static double magX;
	private static String region;

	/**
	 * initialize the javabeans.
	 */
	public input() {
		this.UTC_date_start = null;
		this.UTC_date_end = null;
		this.magS = 0;
		this.magX = 0;
		this.region = null;
	}

	/**
	 * java bean.
	 * @param UTC_date_start
	 * @param UTC_date_end
	 * @param magS
	 * @param magX
	 * @param region
	 */
	public input(String UTC_date_start, String UTC_date_end, double magS, double magX, String region) {
		this.UTC_date_start = UTC_date_start;
		this.UTC_date_end = UTC_date_end;
		this.magS = magS;
		this.magX = magX;
		this.region = region;
	}

	/**
	 * To deal with input condition. Exception handling.
	 * @return state integer.
	 */
	public static int emptyInput() {
		if ((UTC_date_start == null && UTC_date_end == null && magS == 0 && magX == 0 && region == null) || 
				(magS > magX)){
			return 0;
		} else {
			search();
		}
		return 1;
	}

	/**
	 * passing quest statement to database.
	 * @return resultset to pass the quest result to TransformUtil
	 */
	public static ResultSet search() {
		ResultSet rs = null;
		Connection conn = mysqlJDBC.getConnection();
		Statement stmt = mysqlJDBC.getStatement(conn);
		String sql = "select * from quake where id > 0";
		if (UTC_date_start != null) {
			sql += (" and date >= \'" + UTC_date_start + "\'");
		}
		if (UTC_date_end != null) {
			sql += (" and date <= \'" + UTC_date_end + "\'");
		}
		if (magS != 0) {
			sql += (" and magnitude >= " + Double.toString(magS));
		}
		if (magX != 0) {
			sql += (" and magnitude <= " + Double.toString(magX));
		}
		if (region!= UI.WorldWide && region != null) {
			sql += (" and region = \'" + region + "\'");
		}
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
