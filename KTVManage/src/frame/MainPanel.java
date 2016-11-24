package frame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import listener.TableListener;
import service.BookingService;
import service.RoomService;
import service.RoomTypeService;

public class MainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4090036947302273308L;
	private static MainPanel mainPanel;
	private DataTable table;
	private JScrollPane jscrolPane;
	private String dataType;
	private static RoomTypeService rts = null;
	private static RoomService roomService = null;
	private static BookingService bookingService = null;
	static {
		if (rts == null) {
			rts = new RoomTypeService();
		}
		if (roomService == null) {
			roomService = new RoomService();
		}
		if (bookingService == null) {
			bookingService = new BookingService();
		}
	}

	public static MainPanel instance() {
		if (mainPanel == null)
			mainPanel = new MainPanel();
		return mainPanel;
	}

	public MainPanel() {
		mainPanel = this;
		setOpaque(false);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(0, 0, 20, 10));
		table = new DataTable(null, null);
		jscrolPane = new JScrollPane();
		jscrolPane.setBorder(null);
		jscrolPane.setOpaque(false);
		jscrolPane.getViewport().setOpaque(false);
		add(jscrolPane);
		showRoomsBookedData();
	}

	@Override
	public void paint(Graphics g) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/scrollpane.png"));
		Image img = icon.getImage();
		g.drawImage(img, jscrolPane.getX(), jscrolPane.getY(), jscrolPane.getWidth(), jscrolPane.getHeight(), this);
		super.paint(g);
	}

	public DataTable getTable() {
		return table;
	}

	/**
	 * 显示所有房间信息
	 */
	public void showAllRoomsData() {
		dataType = "AllRooms";
		Object[] head = { "房号", "房间楼层", "预定状态", "房间类型", "房间价格", "加钟费用","是否已开通" };
		showData(roomService.showAllRoomInfo(), head);
		JMenuItem book = new JMenuItem("    预定房间   ");
		JMenuItem add = new JMenuItem("    添加房间   ");
		JMenuItem delete = new JMenuItem("    删除房间    ");
		JPopupMenu menu = new JPopupMenu();
		menu.add(book);
		menu.add(add);
		menu.add(delete);
		TableListener tableListener = new TableListener(menu);
		table.addMouseListener(tableListener);
		table.addMouseMotionListener(tableListener);
		book.addActionListener(tableListener);
		add.addActionListener(tableListener);
		delete.addActionListener(tableListener);
	}

	public void showExpiredRoomsData() {
		dataType = "ExpiredRooms";
		Object[] head = { "房号", "房间类型", "房间消费", "开始时间", "结束时间" };
		//showData(RoomDao.instance().getExpiredRoomsData(), head);
		JPopupMenu menu = new JPopupMenu();
		JMenuItem checkout = new JMenuItem("    房间结算    ");
		JMenuItem addHours = new JMenuItem("    房间加钟    ");
		menu.add(checkout);
		menu.add(addHours);
		TableListener tableListener = new TableListener(menu);
		table.addMouseListener(tableListener);
		table.addMouseMotionListener(tableListener);
		checkout.addActionListener(tableListener);
		addHours.addActionListener(tableListener);
	}

	public void showRoomTypesData() {
		dataType = "RoomTypes";
		Object[] head = { "ID", "类型名称", "房间价格", "加钟费用" };
		showData(rts.showRoomTypeInfo(), head);
		JMenuItem add = new JMenuItem("   添加类型    ");
		JMenuItem edit = new JMenuItem("    编辑类型    ");
		JMenuItem delete = new JMenuItem("    删除类型    ");
		JPopupMenu menu = new JPopupMenu();
		menu.add(add);
		menu.add(edit);
		menu.add(delete);
		TableListener tableListener = new TableListener(menu);
		table.addMouseListener(tableListener);
		table.addMouseMotionListener(tableListener);
		add.addActionListener(tableListener);
		edit.addActionListener(tableListener);
		delete.addActionListener(tableListener);
	}

	public void showRoomsBookedData() {
		dataType = "RoomsBooked";
		Object[] head = { "房号", "房间类型", "电话号码", "预定时长", "预定时间" };
		showData(bookingService.showBookingInfo(), head);
		JMenuItem take = new JMenuItem("    开通房间   ");
		JMenuItem delete = new JMenuItem("    删除订单    ");
		JPopupMenu menu = new JPopupMenu();
		menu.add(take);
		menu.add(delete);
		TableListener tableListener = new TableListener(menu);
		table.addMouseListener(tableListener);
		table.addMouseMotionListener(tableListener);
		take.addActionListener(tableListener);
		delete.addActionListener(tableListener);
	}

	public void showRoomTakenData() {
		dataType = "RoomsTaken";
		Object[] head = { "房号", "房间类型", "开始时间", "结束时间" };
		showData(roomService.showRoomTakenInfo(), head);
		JPopupMenu menu = new JPopupMenu();
		JMenuItem checkout = new JMenuItem("    房间结算    ");
		JMenuItem addHours = new JMenuItem("    房间加钟    ");
		JMenuItem  export= new JMenuItem("    导出表格数据    ");
		menu.add(checkout);
		menu.add(addHours);
		menu.add(export);
		TableListener tableListener = new TableListener(menu);
		table.addMouseListener(tableListener);
		table.addMouseMotionListener(tableListener);
		checkout.addActionListener(tableListener);
		addHours.addActionListener(tableListener);
		export.addActionListener(tableListener);
	}


	public void showData(Object[][] data, Object[] head) {
		table.removeAll();
		table = new DataTable(data, head);
		jscrolPane.setViewportView(table);
	}

	public void refresh() {
		if (dataType.equals("AllRooms")) {
			showAllRoomsData();
		} else if (dataType.equals("RoomTypes")) {
			showRoomTypesData();
		} else if (dataType.equals("RoomsBooked")) {
			showRoomsBookedData();
		} else if (dataType.equals("RoomsTaken")) {
			showRoomTakenData();
		} else if (dataType.equals("ExpiredRooms")) {
			showExpiredRoomsData();
		} 
	}
}
