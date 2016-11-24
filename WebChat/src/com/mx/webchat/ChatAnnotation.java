package com.mx.webchat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mx.bean.PrivateRecord;
import com.mx.bean.PublicRecord;
import com.mx.db.UserDao;
import com.mx.db.UserDao2;

@ServerEndpoint(value = "/chat")
public class ChatAnnotation {

	private static final String GUEST_PREFIX = "在线人数：";
	private static final AtomicInteger connectionIds = new AtomicInteger(0);
	private static final Set<ChatAnnotation> connections = new CopyOnWriteArraySet<>();

	private String nickname;
	private Session session;
	private String id;
	private String MAX;

	public ChatAnnotation() {
		MAX = GUEST_PREFIX + connectionIds.getAndIncrement();
	}

	@OnOpen
	public void start(Session session) {
		this.session = session;
		connections.add(this);
	}

	@OnClose
	public void end() {
		System.out.println(this.id + "下线：" + this.nickname);
		connections.remove(this);
		Thread th = new Thread(new OffLineThead(this.id, "0"));// 子线程更新用户登录状态
		th.start();
		deleteUser();
	}

	@OnMessage
	public void incoming(String message) {
		System.out.println("Server收到消息：" + message);
		analysisJSON(Utils.getJSON(message));
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		System.out.println("Chat Error: " + t.toString());
	}

	private static void broadcast(String msg) {
		int i = 1, j = connections.size() - 1;
		for (ChatAnnotation client : connections) {
			try {
				if (i > j)
					break;
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg);
					System.out.println("发送完毕" + msg);
				}
				i++;
			} catch (IOException e) {
				System.out.println("Chat Error: Failed to send message to client");
				connections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
					// Ignore
				}
				String message = String.format("* %s %s", client.nickname, "has been disconnected.");
				broadcast(message);
			}
		}
	}

	private void sendMessage(String msg) {
		try {
			this.session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 解析数据
	 * 
	 * @param obj
	 */
	public void analysisJSON(JSONObject obj) {

		try {
			if (obj.getString("head").equals("add")) {
				this.nickname = obj.getString("name");
				this.id = obj.getString("id");
				System.out.println(this.nickname + "上线了");
				new Thread(new OffLineThead(this.id, "1")).start();// 子线程更新用户登录状态
				sendUserList();
				sendRecord();
				newUser();
				return;
			}
			if (obj.getString("head").equals("groupchat")) {
				sendGroupMessage(obj.getString("msg"));
				new Thread(new SaveRecordThead(this.id, this.nickname, obj.getString("msg"), "5")).start();
				return;
			}
			if (obj.getString("head").equals("hide")) {
				sendHideMessage(obj);
				return;
			}
			if (obj.getString("head").equals("getprivate")) {
				sendPrivateRecord(obj.getString("id"), obj.getString("toid"));
				return;
			}
			if (obj.getString("head").equals("changename")) {
				changeName(obj);
				return;
			}
			if (obj.getString("head").equals("changepswd")) {
				changeInfo(obj);
				return;
			}
			if (obj.getString("head").equals("changena")) {
				changeInfo(obj);
				return;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 用户在线列表
	 * 
	 * @throws JSONException
	 */
	private void sendUserList() throws JSONException {
		JSONArray array = new JSONArray();
		JSONObject jobj = null;
		JSONObject obj = new JSONObject();
		obj.put("code", 1);
		int i = 1, j = connections.size() - 1;
		for (ChatAnnotation client : connections) {
			if (i > j)
				break;
			jobj = new JSONObject();
			jobj.put("id", client.id);
			jobj.put("name", client.nickname);
			array.put(jobj);
			jobj = null;
			i++;
		}
		obj.put("userlist", array);
		sendMessage(obj.toString());
	}

	// 公共聊天记录
	private void sendRecord() {

		List<PublicRecord> lis = new ArrayList<>();
		lis = new UserDao2().getRecored();

		JSONArray contents_list = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject ob;
		try {
			obj.put("code", 6);
			for (PublicRecord li : lis) {
				ob = new JSONObject();
				ob.put("code", Integer.parseInt(li.getCode()));
				ob.put("id", li.getUid());
				ob.put("name", li.getName());
				ob.put("content", li.getContent());
				ob.put("time", li.getTime());
				contents_list.put(ob);
				ob = null;
			}
			obj.put("contents_list", contents_list);
			this.session.getBasicRemote().sendText(obj.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 通知其他用户有新用户上线
	 */
	private void newUser() {
		JSONObject obj = new JSONObject();
		JSONObject user = new JSONObject();
		try {
			obj.put("code", 2);
			user.put("id", this.id);
			user.put("name", this.nickname);
			obj.put("user", user);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		broadcast(obj.toString());
	}

	// 私聊记录
	private void sendPrivateRecord(String uid, String toid) {

		List<PrivateRecord> lis = new ArrayList<>();
		lis = new UserDao2().getPrivateRecored(uid, toid);

		JSONArray contents_list = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject ob;
		try {
			obj.put("code", 8);
			for (PrivateRecord li : lis) {
				ob = new JSONObject();
				ob.put("id", li.getFromid());
				ob.put("name", li.getFromname());
				ob.put("toid", li.getToid());
				ob.put("toname", li.getToname());
				ob.put("toname", li.getToname());
				ob.put("content", li.getContent());
				ob.put("time", li.getTime());
				contents_list.put(ob);
				ob = null;
			}
			obj.put("contents_list", contents_list);
			this.session.getBasicRemote().sendText(obj.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 用户下线通知
	public void deleteUser() {
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		try {
			obj.put("code", 3);
			obj2.put("id", this.id);
			obj2.put("name", "");
			obj2.put("time", time);
			obj.put("user", obj2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (ChatAnnotation client : connections) {
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(obj.toString());
				}
			} catch (IOException e) {

			}
		}
		System.out.println("用户下线通知发送完毕");

	}

	public void sendGroupMessage(String msg) {
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		try {
			obj.put("code", 5);
			obj2.put("id", this.id);
			obj2.put("name", this.nickname);
			obj2.put("content", msg);
			obj2.put("time", time);
			obj.put("user", obj2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (ChatAnnotation client : connections) {
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(obj.toString());
				}
			} catch (IOException e) {

			}
		}
		System.out.println("群消息发送完毕");
	}

	/**
	 * 转发私聊信息
	 */
	private void sendHideMessage(JSONObject js) {

		try {
			String fromid = js.getString("fromid");
			String fromname = this.nickname;
			String toid = js.getString("toid");
			String toname = js.getString("toname");
			String msg = js.getString("msg");
			String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
			new Thread(new SavePrivateRecordThead(fromid, fromname, msg, time, toid, toname)).start();
			JSONObject obj = new JSONObject();
			JSONObject fromuser = new JSONObject();

			obj.put("code", 7);
			fromuser.put("id", fromid);
			fromuser.put("name", fromname);
			fromuser.put("content", msg);
			fromuser.put("time", time);
			obj.put("fromuser", fromuser);

			JSONObject touser = new JSONObject();
			touser.put("id", toid);
			touser.put("name", toname);
			obj.put("touser", touser);

			for (ChatAnnotation client : connections) {
				if (client.nickname.equals(toname)) {
					client.session.getBasicRemote().sendText(obj.toString());
					break;
				}
			}
			this.session.getBasicRemote().sendText(obj.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("私聊消息发送完毕");
	}

	private void changeName(JSONObject ob) {
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		try {
			String chgname = ob.getString("chgname");
			new Thread(new SaveRecordThead(this.id, this.nickname, chgname, "4")).start();
			this.nickname = chgname;
			obj.put("code", 4);
			obj2.put("id", ob.getString("id"));
			obj2.put("name", chgname);
			obj2.put("beforename", ob.getString("name"));
			obj2.put("time", time);
			obj.put("user", obj2);
			for (ChatAnnotation client : connections) {
				synchronized (client) {
					client.session.getBasicRemote().sendText(obj.toString());
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {

		}
	}

	private void changeInfo(JSONObject obj) throws JSONException {

		String code = new UserDao().change(obj.getString("id"), obj.getString("head"), obj.getString("ps"),
				obj.getString("pswd"));
		JSONObject js = new JSONObject();
		js.put("code", 0);
		if (code.equals("OK"))
			js.put("msg", "修改成功");
		else
			js.put("msg", "修改失败");

		try {
			this.session.getBasicRemote().sendText(js.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}