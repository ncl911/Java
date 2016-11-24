package service;

import Imp.ManagerImp;

public class ManagerService {
	private static ManagerImp mi=null;
    static {
    	if(mi==null){
    		mi=new ManagerImp();
    	}
    }
    /**
     * 登录验证֤
     * @param userName
     * @param password
     * @return
     */
    public boolean check(String userName, String password) {
		if(mi.findManager(userName, password)!=null){
			return true;
		}
		return false;
	}
}
