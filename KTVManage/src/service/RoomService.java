package service;

import java.util.List;

import Imp.RoomImp;
import entity.Charge;
import entity.Room;
import entity.ShowRoomInfo;
import entity.ShowRoomTakenInfo;

public class RoomService {
	private static RoomImp ri = null;
	static {
		if (ri == null) {
			ri = new RoomImp();
		}
	}

	/**
	 * 返回所有房间信息
	 * 
	 * @return
	 */
	public Object[][] showAllRoomInfo() {
		List<ShowRoomInfo> list = ri.findAllRoom();
		Object[][] obj = new Object[list.size()][7];
		for (int i = 0; i < list.size(); i++) {
			obj[i][0] = list.get(i).getId();
			obj[i][1] = list.get(i).getFloor();
			obj[i][2] = list.get(i).getBooked();
			obj[i][3] = list.get(i).getTypeName();
			obj[i][4] = list.get(i).getPrice();
			obj[i][5] = list.get(i).getPriceAdded();
			obj[i][6] = list.get(i).getTaken();
		}
		return obj;
	}

	/**
	 * 添加房间
	 * 
	 * @param id
	 * @param floor
	 * @param type
	 */
	public void addRoom(Room room) {
		ri.addRoom(room);
	}

	/**
	 * 查询id号是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean findRoomById(int id) {
		if (ri.getRoomById(id) != null) {
			return true;
		}
		return false;
	}
	
	public Room getRoomById(int id) {
		Room room=null;
		if (ri.getRoomById(id) != null) {
			room=ri.getRoomById(id);
		}
		return room;
	}

	/**
	 * 根据ID删除房间
	 * 
	 * @param id
	 */
	public void deleteRoomById(int id) {
		ri.deleteRoomById(id);
	}

	/**
	 * 更新预定状态
	 * 
	 * @param id
	 * @param booked
	 */
	public void updateBooked(int id, int booked) {
		ri.updateBooked(id, booked);
	}

	/**
	 * 设置开房时间和结束时间
	 * 
	 * @param takenTime
	 * @param endTime
	 */
	public void setTime(int id, String takenTime, String endTime) {
		ri.setTime(id, takenTime, endTime);
	}

	/**
	 * 显示已开通房间的信息
	 * 
	 * @return
	 */
	public Object[][] showRoomTakenInfo() {
		List<ShowRoomTakenInfo> list = ri.getRoomTakenInfo();
		Object[][] objects = null;
		if (list.size() > 0) {
			objects = new Object[list.size()][4];
			for (int i = 0; i < list.size(); i++) {
				objects[i][0] = list.get(i).getId();
				objects[i][1] = list.get(i).getTypeName();
				objects[i][2] = list.get(i).getStartTime();
				objects[i][3] = list.get(i).getEndTime();
			}
		}else{
			//System.out.println("null");
		}
		return objects;
	}

	/**
	 * 开通房间
	 * 
	 * @param id
	 * @param takenValue
	 */
	public void takenRoom(int id, String takenValue) {
		ri.updateTakenById(id, takenValue);
	}
	/**
	 * 加时
	 * @param id
	 * @param time
	 */
	public void addTime(int id,String time){
		ri.addTime(id, time);
	}
	/**
	 * 结算
	 * @param id
	 * @return
	 */
	public Charge charge(int id){
		
		return ri.charge(id);
	}
	/**
	 * 结束
	 * @param id
	 */
	public void endRoom(int id){
		ri.endRoom(id);
	}

}
