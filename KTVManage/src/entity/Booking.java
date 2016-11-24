package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {
	private int roomId;
	private String phoneNumber;
	private int hours;
	private String bookTime;

	public Booking(int roomId,String phoneNumber, int hours) {
		this.roomId = roomId;
		this.phoneNumber = phoneNumber;
		this.hours = hours;
		this.bookTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date());
	}

	public int getRoomId() {
		return roomId;
	}

	public String getRoomNumber() {
		String tmpStr = "0000"+roomId;
		return tmpStr.substring(tmpStr.length() - 4,tmpStr.length());
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getHours() {
		return hours;
	}

	public String getBookTime() {
		return bookTime;
	}

}
