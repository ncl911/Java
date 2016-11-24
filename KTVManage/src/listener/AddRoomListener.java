package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entity.Room;
import frame.AddRoomDialog;
import frame.MainPanel;
import service.RoomService;
import service.RoomTypeService;

public class AddRoomListener implements ActionListener {
	private JComboBox jcbRoomType;
	private JTextField jtfRoomNumber, jtbFloor;
	private JButton cancel;
	private static RoomService roomService = null;
	private static RoomTypeService roomTypeService = null;
	static {
		if (roomService == null) {
			roomService = new RoomService();
		}
		if (roomTypeService == null) {
			roomTypeService = new RoomTypeService();
		}
	}

	public AddRoomListener(JComboBox jcbRoomType, JTextField jtfRoomNumber, JTextField jtbFloor, JButton cancel) {
		this.jcbRoomType = jcbRoomType;
		this.jtfRoomNumber = jtfRoomNumber;
		this.jtbFloor = jtbFloor;
		this.cancel = cancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancel) {
			AddRoomDialog.instance().dispose();
		} else {
			if (jtfRoomNumber.getText().equals("") || jtbFloor.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "请将信息填写完整！");
				return;
			}
			int roomId = Integer.parseInt(jtfRoomNumber.getText());
			if (roomService.findRoomById(roomId)) {
				JOptionPane.showMessageDialog(null, "<html>房号<b><font size=8>" + roomId + "</font></b>已存在！", "提示ʾ",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int roomType = roomTypeService.getRoomTypeIdByName(jcbRoomType.getSelectedItem().toString());
			roomService.addRoom(new Room(roomId, Integer.parseInt(jtbFloor.getText()), roomType,0,"N"));
			MainPanel.instance().refresh();
			AddRoomDialog.instance().dispose();
			JOptionPane.showMessageDialog(null, "添加成功！");
		}
	}
}
