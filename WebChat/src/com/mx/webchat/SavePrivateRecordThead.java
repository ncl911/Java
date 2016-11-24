package com.mx.webchat;

import com.mx.bean.PrivateRecord;
import com.mx.db.UserDao2;

public class SavePrivateRecordThead implements Runnable {

	private PrivateRecord pr;

	public SavePrivateRecordThead(String fromid, String fromname, String content, String time, String toid,
			String toname) {
		// TODO Auto-generated constructor stub
		pr = new PrivateRecord();
		pr.setFromid(fromid);
		pr.setFromname(fromname);
		pr.setContent(content);
		pr.setTime(time);
		pr.setToid(toid);
		pr.setToname(toname);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new UserDao2().savePrivateRecord(pr);
	}

}
