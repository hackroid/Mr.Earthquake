package com;

import java.sql.*;

public class sqliteJDBC {

	public static Connection dbConnection() {
		try {
			// CLASSPATH must be properly set, for instance on
			// a Linux system or a Mac:
			// $ export CLASSPATH=.:sqlite-jdbc-version-number.jar
			// Alternatively, run the program with
			// $ java -cp .:sqlite-jdbc-version-number.jar BasicJDBC
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			System.err.println("Cannot find the driver.");
			System.exit(1);
		}
		try {
			Connection con = null;
			con = DriverManager.getConnection("jdbc:sqlite:" + "source/earthquakes-1.sqlite");
			con.setAutoCommit(false);
			System.err.println("Successfully connected to the database.");
			return con;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public static void dbClose(Connection con) {
		if (con != null) {
			try {
				con.close();
				con = null;
			} catch (Exception e) {
				// Forget about it
			}
		}
	}

}
