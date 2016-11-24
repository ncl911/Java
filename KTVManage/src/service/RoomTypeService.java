package service;

import java.util.List;

import Imp.RoomTypeImp;
import entity.RoomType;

public class RoomTypeService {
	private static RoomTypeImp rtp = null;
	static {
		if (rtp == null) {
			rtp = new RoomTypeImp();
		}
	}

	/**
	 * 返回房间类型信息
	 * 
	 * @return
	 */
	public Object[][] showRoomTypeInfo() {
		List<RoomType> list = rtp.findAllType();
		Object[][] obj = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			obj[i][0] = list.get(i).getId();
			obj[i][1] = list.get(i).getName();
			obj[i][2] = list.get(i).getPrice();
			obj[i][3] = list.get(i).getPriceAdded();
		}
		return obj;
	}

	/**
	 * 修改房间类型信息
	 * 
	 * @param id
	 * @param rt
	 */
	public void EditRoomTypeInfo(int id, RoomType rt) {
		rtp.EditRoomTypeInfo(id, rt);
	}

	/**
	 * 根据房间类型ID获取信息
	 * 
	 * @param id
	 * @return
	 */
	public RoomType getRoomTypeInfoById(int id) {
		return rtp.getRoomTypeInfoById(id);
	}

	/**
	 * 添加房间类型
	 * 
	 * @param name
	 * @param price
	 * @param priceadded
	 */
	public void addRoomType(String name, int price, int priceadded) {
		rtp.addRoomType(name, price, priceadded);
	}

	/**
	 * 根据ID删除所选的房间类型
	 * 
	 * @param id
	 */
	public void deleteRoomTypeById(int id) {
		rtp.deleteRoomTypeById(id);
	}
/**
 * 返回所有房间类型名称
 * @return
 */
	public Object[] getRoomTypeName() {
		List<String> list = rtp.getRoomTypeName();
		Object[] objects = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			objects[i] = list.get(i);
		}
		return objects;
	}
	
	public int getRoomTypeIdByName(String typeName){
		return rtp.getRoomTypeIdByName(typeName);
	}
}
