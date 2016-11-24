package entity;

public class Room {
	private int id;
	public void setId(int id) {
		this.id = id;
	}

	private int floor;
	private int type;
	private String startTime;
	private String endTime;
	private int booked = 0;
	private String taken="N";

	public Room(){
		
	}
	public Room(int id, int floor, int type,int booked,String taken) {
		this.id = id;
		this.floor = floor;
		this.type = type;
		this.booked=booked;
		this.taken=taken;
	}

	public int getId() {
		return id;
	}

	public String getNumber() {
		String tmpStr = "0000" + id;
		return tmpStr.substring(tmpStr.length() - 4, tmpStr.length());
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getType() {
		return type;
	}

	public void setType(int size) {
		this.type = size;
	}

	/*public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}*/

	/*public int getPrice() {
		return RoomTypeDao.instance().getPricebyId(type);
	}

	public int getPriceAdded() {
		return RoomTypeDao.instance().getPriceAddedbyId(type);
	}*/


	public int getBooked() {
		return booked;
	}

	public void setBooked(int booked) {
		this.booked = booked;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTaken() {
		return taken;
	}

	public void setTaken(String taken) {
		this.taken = taken;
	}
}
