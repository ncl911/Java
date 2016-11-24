package Imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DBUtil;
import dao.IRoomType;
import entity.RoomType;

public class RoomTypeImp implements IRoomType {
	private static DBUtil dbutil = null;
	static {
		if (dbutil == null) {
			dbutil = new DBUtil();
		}
	}
	private Statement statement = null;
	private ResultSet rs = null;
	private Connection conn = null;

	@Override
	public List<RoomType> findAllType() {
		List<RoomType> list = new ArrayList<>();
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = dbutil.getConnection().createStatement();
				String sql = "select * from roomType";
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					RoomType rt = new RoomType();
					rt.setId(rs.getInt(1));
					rt.setName(rs.getString(2));
					rt.setPrice(rs.getInt(3));
					rt.setPriceAdded(rs.getInt(4));
					list.add(rt);
					rt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}
		return list;
	}

	@Override
	public void EditRoomTypeInfo(int id, RoomType rt) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = dbutil.getConnection().createStatement();
				String sql = "update roomtype set name='" + rt.getName() + "',price=" + rt.getPrice() + ",priceAdded="
						+ rt.getPriceAdded() + " where id=" + id + "";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}

	}

	@Override
	public RoomType getRoomTypeInfoById(int id) {
		conn = dbutil.getConnection();
		RoomType roomType = null;
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "select * from roomtype where id=" + id + "";
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					roomType = new RoomType(id, rs.getString(2), rs.getInt(3), rs.getInt(4));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}
		return roomType;
	}

	@Override
	public void addRoomType(String name, int price, int priceadded) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "insert into roomtype (name,price,priceAdded) values('" + name + "'," + price + "," + priceadded + ")";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}

	}

	@Override
	public void deleteRoomTypeById(int id) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "delete from roomtype where id=" + id + "";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dbutil.closed(rs, statement, conn);
			}
		}
	}

	@Override
	public List<String> getRoomTypeName() {
		List<String> list=new ArrayList<>();
		conn=dbutil.getConnection();
		if(conn!=null){
			try {
				statement=conn.createStatement();
				String sql="select name from roomtype";
				rs=statement.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				dbutil.closed(rs, statement, conn);
			}
		}
		return list;
	}

	@Override
	public int getRoomTypeIdByName(String typeName) {
		conn=dbutil.getConnection();
		int roomTypeId=0;
		if(conn!=null){
			try {
				statement=conn.createStatement();
				String sql="select id from roomtype where name='"+typeName+"'";
				rs=statement.executeQuery(sql);
				while(rs.next()){
					roomTypeId=rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				dbutil.closed(rs, statement, conn);
			}
		}
		return roomTypeId;
	}

}
