package entity;

public class ShowRoomInfo {
	private int id;
	private int floor;
	private int booked;
	private String typeName;
	private int price;
	private int priceAdded;
	private String taken;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getBooked() {
		return booked;
	}

	public void setBooked(int booked) {
		this.booked = booked;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPriceAdded() {
		return priceAdded;
	}

	public void setPriceAdded(int priceAdded) {
		this.priceAdded = priceAdded;
	}

	public String getTaken() {
		return taken;
	}

	public void setTaken(String taken) {
		this.taken = taken;
	}

}
