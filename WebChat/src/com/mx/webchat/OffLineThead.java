package com.mx.webchat;

import com.mx.db.UserDao;

public class OffLineThead implements Runnable{

	private String id="";
	private String code="0";
	public OffLineThead(String na,String cd) {
		// TODO Auto-generated constructor stub
		this.id=na;
		this.code=cd;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		new UserDao().updateCode(id,code);
	}

}
