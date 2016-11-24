package dao;

import java.util.List;

import entity.RoomType;

public interface IRoomType {
	public List<RoomType> findAllType();
	public void EditRoomTypeInfo(int id,RoomType rt);
    public RoomType getRoomTypeInfoById(int id);
    public void addRoomType(String name,int price,int priceadded);
    public void deleteRoomTypeById(int id);
    public List<String> getRoomTypeName();
    public int getRoomTypeIdByName(String typeName);
}
