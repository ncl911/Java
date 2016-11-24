package dao;

import entity.Manager;

public interface IManager {
	public Manager findManager(String userName,String password);

}
