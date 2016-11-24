package Imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DBUtil;
import dao.IBooking;
import entity.Booking;
import entity.ShowBookingInfo;

public class BookingImp implements IBooking {

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
	public boolean findBookingInfoById(int id) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "select * from booking where roomId=" + id + "";
				rs = statement.executeQuery(sql);
				if (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}
		return false;
	}

	@Override
	public void addBookingRoom(Booking booking) {
		// TODO Auto-generated method stub
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "insert into booking (roomId,phoneNumber,hours,bookTime) " + "values("
						+ booking.getRoomId() + ",'" + booking.getPhoneNumber() + "'," + booking.getHours() + ",'"
						+ booking.getBookTime() + "')";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}
	}

	@Override
	public List<ShowBookingInfo> getBookingInfo() {
		List<ShowBookingInfo> list = new ArrayList<>();
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "select booking.roomId,roomtype.name,booking.phoneNumber,booking.hours,booking.bookTime"
						+ " from booking inner join room on booking.roomId=room.id inner join roomtype on room.type=roomtype.id";
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					ShowBookingInfo ski = new ShowBookingInfo();
					ski.setRoomNum(rs.getInt(1));
					ski.setTypeName(rs.getString(2));
					ski.setPhoneNum(rs.getString(3));
					ski.setHours(rs.getInt(4));
					ski.setBookingTime(rs.getString(5));

					list.add(ski);
					ski = null;
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
	public void deleteBookingById(int id) {
		conn = dbutil.getConnection();
		if (conn != null) {
			try {
				statement = conn.createStatement();
				String sql = "delete from booking where roomId=" + id + "";
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbutil.closed(rs, statement, conn);
			}
		}
	}

	
}
