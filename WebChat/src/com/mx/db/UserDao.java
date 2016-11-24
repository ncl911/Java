package com.mx.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mx.webchat.Utils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UserDao {

	/**
	 * 验证用户名和密码
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public String isUser(String name, String password) {
		ConnectionDB connectionDB = new ConnectionDB();
		Connection conn = null;
		Statement sta = null;
		String isuser = "ERROR";
		try {
			conn = connectionDB.getConnection();
			sta = (Statement) conn.createStatement();
			String sql = "select * from users where name='" + name + "' and password='" + password + "'";
			ResultSet set = sta.executeQuery(sql);
			if (set.next()) {
				if (set.getString("code").equals("1")) {
					isuser = "ONLINE";
				} else {
					String uid = set.getString("u_id");
					isuser = uid;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionDB.closeDB(sta, conn);
		}
		return isuser;
	}

	/**
	 * 注册信息存入数据库
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public String checkUser(String name, String password) {
		String isuser = "FAIL";
		String uid = Utils.getRandomString(8);// 随机产生uid
		while (checkuid(uid)) {
			uid = Utils.getRandomString(8);// 随机产生uid
		}

		ConnectionDB connectionDB = new ConnectionDB();
		Connection conn = null;
		Statement sta = null;
		try {
			conn = connectionDB.getConnection();
			sta = (Statement) conn.createStatement();
			String sql = "select * from users where name='" + name + "'";
			ResultSet set = sta.executeQuery(sql);
			if (set.next()) {
				isuser = "ERROR";
			} else {
				String sql2 = "insert into users(u_id,name,password)values('" + uid + "','" + name + "','" + password
						+ "')";
				int isuse = sta.executeUpdate(sql2);
				System.out.println("-==" + isuse);
				if (isuse == 1)
					isuser = "OK";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionDB.closeDB(sta, conn);
		}
		return isuser;
	}

	public boolean checkuid(String uid) {

		ConnectionDB connectionDB = new ConnectionDB();
		Connection conn = null;
		Statement sta = null;
		boolean isuser = false;
		try {
			conn = connectionDB.getConnection();
			sta = (Statement) conn.createStatement();
			String sql = "select * from users where u_id='" + uid + "'";
			ResultSet set = sta.executeQuery(sql);
			if (set.next()) {
				isuser = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionDB.closeDB(sta, conn);
		}
		return isuser;

	}

	public void updateCode(String uid, String code) {
		ConnectionDB connectionDB = new ConnectionDB();
		Connection conn = null;
		Statement sta = null;
		System.out.println("code=" + code);
		try {
			conn = connectionDB.getConnection();
			sta = (Statement) conn.createStatement();
			String sql2 = "UPDATE  users SET code='" + code + "' WHERE u_id='" + uid + "'";
			sta.executeUpdate(sql2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionDB.closeDB(sta, conn);
		}
	}

	public String change(String uid, String code,String ps, String data) {
		ConnectionDB connectionDB = new ConnectionDB();
		Connection conn = null;
		Statement sta = null;
		System.out.println("code=" + code);
		String sql2 = "";
		String str="ERROR";
		try {
			conn = connectionDB.getConnection();
			sta = (Statement) conn.createStatement();
			if (code.equals("changepswd"))
				sql2 = "UPDATE  users SET password='" + data + "' WHERE u_id='" + uid + "' and password='"+ps+"'";
			else
				sql2 = "UPDATE  users SET name='" + data + "' WHERE u_id='" + uid + "' and password='"+ps+"'";
			sta.executeUpdate(sql2);
			str="OK";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionDB.closeDB(sta, conn);
		}
		return str;
	}

}
