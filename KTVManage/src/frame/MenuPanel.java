package frame;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import listener.MenuListener;

public class MenuPanel extends JPanel {
	/**
	 * MenuPanel
	 */
	private static final long serialVersionUID = -2703153422697822701L;
	private static MenuPanel menuPanel;

	public static MenuPanel instance() {
		if (menuPanel == null)
			menuPanel = new MenuPanel();
		return menuPanel;
	}

	public MenuPanel() {
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 15));
		setPreferredSize(new Dimension(200, 600));

		ImageButton jbtRoomsBooked, jbtRoomsTaken, jbtRooms, jbtRoomTypes;
		jbtRoomsBooked = new ImageButton("menu", "已定房间", 24);
		jbtRoomsTaken = new ImageButton("menu", "已拿房间", 24);
		jbtRooms = new ImageButton("menu", "房间管理", 24);
		jbtRoomTypes = new ImageButton("menu", "房间类型", 24);

		jbtRoomsBooked.setPreferredSize(new Dimension(180, 50));
		jbtRoomsTaken.setPreferredSize(new Dimension(180, 50));
		jbtRooms.setPreferredSize(new Dimension(180, 50));
		jbtRoomTypes.setPreferredSize(new Dimension(180, 50));

		MenuListener menuListener = new MenuListener(jbtRoomsBooked,
				jbtRoomsTaken, jbtRooms, jbtRoomTypes);
		jbtRoomsBooked.addActionListener(menuListener);
		jbtRoomsTaken.addActionListener(menuListener);
		jbtRooms.addActionListener(menuListener);
		jbtRoomTypes.addActionListener(menuListener);
		add(jbtRoomsBooked);
		add(jbtRoomsTaken);
		add(jbtRooms);
		add(jbtRoomTypes);
	}
}