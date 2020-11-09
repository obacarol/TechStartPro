package com.br.olist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDerby {

	public static Connection connection;
	
	static {
		try {
			connection = DriverManager.getConnection("jdbc:derby://localhost:1527/olist;create=true;", "adm","adm");
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
