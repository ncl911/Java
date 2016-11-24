package com.mx.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mx.bean.PrivateRecord;
import com.mx.bean.PublicRecord;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UserDao2 {

	/**
	 * 保存公共聊天记录
	 * 
	 * @param uid
	 * @param name
	 * @param content
	 */
	public void saveRecord(String uid, String name, String content,String code) {
		ConnectionDB connectionDB = new ConnectionDB();
		Connection conn = null;
		Statement sta = null;
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		try {
			conn = connectionDB.getConnection();
			sta = (Statement) conn.createStatement();
			String sql2 = "insert into publicrecord(u_id,name,content,time,code)values('" + uid + "','" + name + "','"
					+ content + "','" + time + "','"+code+"')";
			sta.executeUpdate(sql2);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionDB.closeDB(sta, conn);
		}
	}

	/**
	 * 获取公共聊天记录
	 * 
	 * @return
	 */
	public List<PublicRecord> getRecored() {
		List<PublicRecord> list = new ArrayList<>();
		PublicRecord pr;
		ConnectionDB connectionDB = new ConnectionDB();
		Connection conn = null;
		Statement sta = null;
		ResultSet set = null;
		try {
			conn = connectionDB.getConnection();
			sta = (Statement) conn.createStatement();
			String sql = "select * from publicrecord";
			set = sta.executeQuery(sql);
			while (set.next()) {
				pr = new PublicRecord();
				pr.setUid(set.getString("u_id"));
				pr.setName(set.getString("name"));
				pr.setContent(set.getString("content"));
				pr.setTime(set.getString("time"));
				pr.setCode(set.getString("code"));
				list.add(pr);
				pr = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionDB.closeDB(sta, conn);
		}
		return list;
	}

	/**
	 * 保存私聊记录
	 * 
	 * @param pr
	 */
	public void savePrivateRecord(PrivateRecord pr) {
		ConnectionDB connectionDB = new ConnectionDB();
		Connection conn = null;
		Statement sta = null;
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		try {
			conn = connectionDB.getConnection();
			sta = (Statement) conn.createStatement();
			String sql2 = "insert into privaterecord(fromid,fromname,content,time,toid,toname)values('" + pr.getFromid()
					+ "','" + pr.getFromname() + "','" + pr.getContent() + "','" + time + "','" + pr.getToid() + "','"
					+ pr.getToname() + "')";
			sta.executeUpdate(sql2);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionDB.closeDB(sta, conn);
		}
	}

	/**
	 * 获取私聊记录
	 * 
	 * @param uid
	 * @return
	 */
	public List<PrivateRecord> getPrivateRecored(String fromid, String toid) {
		List<PrivateRecord> list = new ArrayList<>();
		PrivateRecord pr;
		ConnectionDB connectionDB = new ConnectionDB();
		Connection conn = null;
		Statement sta = null;
		ResultSet set = null;
		try {
			conn = connectionDB.getConnection();
			sta = (Statement) conn.createStatement();
			String sql = "select * from privaterecord WHERE fromid='" + fromid + "' and toid='" + toid
					+ "' OR fromid='" + toid + "' and toid='" + fromid + "'";
			set = sta.executeQuery(sql);
			while (set.next()) {
				pr = new PrivateRecord();
				pr.setFromid(set.getString("fromid"));
				pr.setFromname(set.getString("fromname"));
				pr.setContent(set.getString("content"));
				pr.setTime(set.getString("time"));
				pr.setToid(set.getString("toid"));
				pr.setToname(set.getString("toname"));
				list.add(pr);
				pr = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionDB.closeDB(sta, conn);
		}
		return list;
	}
}
