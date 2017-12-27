/**
 *MYSQL JDBC Connector
 */
package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

public class mysqlJDBC {
	/**
	 *
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Properties prop = new Properties();
			BufferedReader conf = new BufferedReader(new FileReader("mysql.cnf"));
			prop.load(conf);
			String className = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String USER = prop.getProperty("USER");
			String PASSWORD = prop.getProperty("PASSWORD");
			Class.forName(className);
			conn = DriverManager.getConnection(url, USER, PASSWORD);
			return conn;
		} catch (SQLException se) {
			se.printStackTrace();
			return conn;
		}catch(FileNotFoundException fe){
			System.err.println("cannot open the property file or no such file");
			System.exit(1);
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
