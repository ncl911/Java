package Imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DBUtil;
import dao.IRoom;
import entity.Charge;
import entity.Room;
import entity.ShowRoomInfo;
import entity.ShowRoomTakenInfo;

public class RoomImp implements IRoom {

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
	public List<ShowRoomInfo> findAllRoom() {
		conn = dbutil.getConnection();
		List<ShowRoomInfo> list = new ArrayList<>();
		ShowRoomInfo sri = null;
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "select room.id,room.floor,room.booked,roomtype.name,"
						+ "roomtype.price,roomtype.priceAdded,room.taken from room inner join roomtype on "
						+ "room.type=roomtype.id";
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					sri = new ShowRoomInfo();
					sri.setId(rs.getInt(1));
					sri.setFloor(rs.getInt(2));
					sri.setBooked(rs.getInt(3));
					sri.setTypeName(rs.getString(4));
					sri.setPrice(rs.getInt(5));
					sri.setPriceAdded(rs.getInt(6));
					sri.setTaken(rs.getString(7));
					list.add(sri);
					sri = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public void addRoom(Room room) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "insert into room (id,floor,type,booked,taken) values(" + room.getId() + ","
						+ room.getFloor() + "," + room.getType() + "," + room.getBooked() + ",'" + room.getTaken()
						+ "')";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}

	}

	@Override
	public Room getRoomById(int id) {
		conn = dbutil.getConnection();
		Room room = null;
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "select * from room where id=" + id + " ";
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					room = new Room();
					room.setId(rs.getInt(1));
					room.setFloor(rs.getInt(2));
					room.setType(rs.getInt(3));
					room.setStartTime(rs.getString(4));
					room.setEndTime(rs.getString(5));
					room.setBooked(rs.getInt(6));
					room.setTaken(rs.getString(7));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return room;
	}

	@Override
	public void deleteRoomById(int id) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "delete from room where id=" + id + "";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}

		}

	}

	@Override
	public void updateBooked(int id, int booked) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "update room set booked=" + booked + " where id=" + id + "";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}

		}

	}

	@Override
	public void setTime(int id, String takenTime, String endTime) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "update room set startTime='" + takenTime + "',endTime='" + endTime + "' where id=" + id
						+ "";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}

		}

	}

	@Override
	public List<ShowRoomTakenInfo> getRoomTakenInfo() {
		List<ShowRoomTakenInfo> list = null;
		conn = dbutil.getConnection();
		if (conn != null) {
			list = new ArrayList<>();
			try {
				statement = conn.createStatement();
				String sql = "select room.id,roomtype.name,room.startTime,room.endTime "
						+ "from room inner join roomtype on room.type=roomtype.id " + "where room.taken='Y'";
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					ShowRoomTakenInfo srti = new ShowRoomTakenInfo();
					srti.setId(rs.getInt(1));
					srti.setTypeName(rs.getString(2));
					srti.setStartTime(rs.getString(3));
					srti.setEndTime(rs.getString(4));
					list.add(srti);
					srti = null;
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
	public void updateTakenById(int id, String takenValue) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "update room set taken='" + takenValue + "' where id=" + id + "";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}

	}

	@Override
	public void addTime(int id, String time) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "update room set endTime='" + time + "' where id=" + id + "";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}

	}

	@Override
	public Charge charge(int id) {
		Charge c = null;
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "select room.id,roomtype.price,roomtype.priceAdded from "
						+ "room inner join roomtype on room.type=roomtype.id where room.id=" + id + "";
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					c = new Charge();
					c.setId(rs.getInt(1));
					c.setPrice(rs.getInt(2));
					c.setPriveAdded(rs.getInt(3));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}
		return c;
	}

	@Override
	public void endRoom(int id) {
		conn=dbutil.getConnection();
		if(conn!=null){
			try {
				statement=conn.createStatement();
				String sql="update room set startTime='0000-00-00 00:00:00',endTime='0000-00-00 00:00:00',"
						+ "taken='N' where id="+id+"";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				dbutil.closed(rs, statement, conn);
			}
		}
		
	}

}
