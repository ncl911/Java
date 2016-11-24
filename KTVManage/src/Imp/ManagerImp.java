package Imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.DBUtil;
import dao.IManager;
import entity.Manager;

public class ManagerImp implements IManager {
	private static DBUtil dbutil=null;
	static {
		if(dbutil==null){
			dbutil=new DBUtil();
		}
	}
	private Statement statement = null;
	private ResultSet rs = null;
	private Connection conn = null;

	@Override
	public Manager findManager(String userName, String password) {
		Manager manager=null;
		conn=dbutil.getConnection();
		if(conn!=null){
			try {
				statement=conn.createStatement();
				String sql="select * from manager where userName='"+userName+"' and password='"+password+"'";
				rs=statement.executeQuery(sql);
				while(rs.next()){
					manager=new Manager();
					manager.setUserName(rs.getString(1));
					manager.setPassword(rs.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return manager;
	}
	
	
}
