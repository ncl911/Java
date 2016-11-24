package dao;

import java.util.List;

import entity.Charge;
import entity.Room;
import entity.ShowRoomInfo;
import entity.ShowRoomTakenInfo;

public interface IRoom {
	public List<ShowRoomInfo> findAllRoom();
	public void addRoom(Room room);
	public Room getRoomById(int id);
	public void deleteRoomById(int id);
	public void updateBooked(int id,int booked);
	public void setTime(int id,String takenTime,String endTime);
    public List<ShowRoomTakenInfo> getRoomTakenInfo();
	public void updateTakenById(int id, String takenValue);
	public void addTime(int id,String time);
	public Charge charge(int id);
	public void endRoom(int id);
}
