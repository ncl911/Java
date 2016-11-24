package service;

import java.util.List;

import Imp.BookingImp;
import entity.Booking;
import entity.ShowBookingInfo;

public class BookingService {
	private static BookingImp bi = null;
	static {
		if (bi == null) {
			bi = new BookingImp();
		}
	}

	/**
	 * 添加预定房间
	 * 
	 * @param booking
	 */
	public void addBookingRoom(Booking booking) {
		bi.addBookingRoom(booking);
	}

	/**
	 * 获取所有预定信息
	 * 
	 * @return
	 */
	public Object[][] showBookingInfo() {
		List<ShowBookingInfo> list = bi.getBookingInfo();
		Object[][] objects = null;
		if (list.size() != 0) {

			objects = new Object[list.size()][5];
			for (int i = 0; i < list.size(); i++) {
				objects[i][0] = list.get(i).getRoomNum();
				objects[i][1] = list.get(i).getTypeName();
				objects[i][2] = list.get(i).getPhoneNum();
				objects[i][3] = list.get(i).getHours();
				objects[i][4] = list.get(i).getBookingTime();
			}
		}
		return objects;
	}
	/**
	 * 删除订单
	 * @param id
	 */
	public void deleteBookingById(int id){
		bi.deleteBookingById(id);
	}
	

}
