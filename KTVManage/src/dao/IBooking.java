package dao;

import java.util.List;

import entity.Booking;
import entity.ShowBookingInfo;

public interface IBooking {
	public boolean findBookingInfoById(int id);
	public List<ShowBookingInfo> getBookingInfo();
	public void addBookingRoom(Booking booking);
    public void deleteBookingById(int id);
}
