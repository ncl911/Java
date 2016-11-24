package com.mx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mx.db.UserDao;

public class LoginServlet extends HttpServlet {

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
		// 获取所有的cookie值
		Cookie[] cookies = req.getCookies();
		Cookie cookie = null;
		if(cookies!=null){
		 for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().equals("isonline")) {
				String isline = cookie.getValue();
				if (isline.equals("online")) {
					req.setAttribute("msg", "x同一浏览器不能登录两个账号");
					RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
					dispatcher.forward(req, resp);
					return;
				}
			}

		}
		}
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		System.out.println(name + "/login/" + password);
		UserDao ud = new UserDao();
		String code = ud.isUser(name, password);
		System.out.println(code);
		if (code.equals("ONLINE")) {
			req.setAttribute("msg", "x该账号已登录");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, resp);
		} else if (code.equals("ERROR")) {
			req.setAttribute("msg", "x用户名或密码错误");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
			dispatcher.forward(req, resp);
		} else {
			Cookie userid = new Cookie("user_id", URLEncoder.encode(code, "UTF-8"));
			Cookie username = new Cookie("user_name", URLEncoder.encode(name, "UTF-8"));
			Cookie isonline = new Cookie("isonline", URLEncoder.encode("online", "UTF-8"));
			resp.addCookie(userid);
			resp.addCookie(username);
			resp.addCookie(isonline);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/mx.jsp");
			dispatcher.forward(req, resp);

		}
	}

}
