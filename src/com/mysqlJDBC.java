//MYSQL JDBC Connector
/**
 *
 */
package com;

import java.sql.*;

public class mysqlJDBC {
	/**
	 *
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://35.201.211.143:3306/earthquake?autoReconnect=true&useSSL=false", "test", "1234");
			return conn;
		} catch (SQLException se) {
			se.printStackTrace();
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return conn;
		}
	}

	/**
	 *
	 * @param conn
	 * @return Statment
	 */
	public static Statement getStatement(Connection conn) {
		Statement stmt = null;
		try	{
			conn = mysqlJDBC.getConnection();
			if (conn != null) {
				System.out.print("Connection Established");
			}
			stmt = conn.createStatement();
			return stmt;
		} catch (SQLException se) {
			se.printStackTrace();
			return stmt;
		} catch (Exception e) {
			e.printStackTrace();
			return stmt;
		}
	}
}
