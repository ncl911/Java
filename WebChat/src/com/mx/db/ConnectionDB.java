package com.mx.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ConnectionDB {

	public Connection getConnection() {
		String className = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/webchat";
		String name = "root";
		String password = "ncl";
		Connection con = null;
		try {
			Class.forName(className).newInstance();
			con = (Connection) DriverManager.getConnection(url, name, password);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;

	}

	public void closeDB(Statement sta, Connection conn) {
		// 关闭数据库连接（无结果集）
		try {
			sta.close();
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void closeDB(ResultSet rs, Statement sta, Connection conn) {
		// 关闭数据库连接（有结果集）
		try {
			rs.close();
			sta.close();
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
