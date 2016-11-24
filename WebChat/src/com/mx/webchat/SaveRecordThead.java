package com.mx.webchat;

import com.mx.db.UserDao2;

public class SaveRecordThead implements Runnable{

	private String uid;
	private String name;
	private String content;
	private String code;
	
	public SaveRecordThead(String uid,String name,String content,String code) {
		// TODO Auto-generated constructor stub
		this.uid=uid;
		this.name=name;
		this.content=content;
		this.code=code;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("保存公共聊天记录"+code);
		new UserDao2().saveRecord(uid, name, content,code);
	}

}
