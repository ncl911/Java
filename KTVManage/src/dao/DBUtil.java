package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	private static String url;
	private static String userName;
	private static String password;
	private static String driverClass;

	static {
		Properties pro = new Properties();
		InputStream in = DBUtil.class.getResourceAsStream("/jdbc.properties");
		try {
			pro.load(in);
			url = pro.getProperty("url");
			userName = pro.getProperty("userName");
			password = pro.getProperty("password");
			driverClass = pro.getProperty("driverClass");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * 连接数据库
 * @return
 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
/**
 * 关闭连接
 * @param rs
 * @param statement
 * @param conn
 */
	public void closed(ResultSet rs, Statement statement, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	/*
	 * public static void main(String [] args){ DBUtil d=new DBUtil(); }
	 */

}
