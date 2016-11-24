package com.mx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mx.db.UserDao;

public class RegisterServlet extends HttpServlet {

	/**
	 * MHJ
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String name = req.getParameter("rname");
		String password = req.getParameter("rpassword");
		System.out.println(name+"//"+password);
		UserDao ud = new UserDao();
		String code = ud.checkUser(name, password);
		 PrintWriter out = resp.getWriter();
		if (code.equals("OK")) {
			out.println("注册成功，请登录");		
		} else if (code.equals("ERROR")) {
			out.println("x用户名已被占用");
		} else {			
			out.println("x注册失败");
		}
	}

}
