package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entity.Booking;
import frame.BookRoomDialog;
import frame.MainPanel;
import service.BookingService;
import service.RoomService;

public class BookRoomListener implements ActionListener {
	private JTextField jtfRoomNum;
	private JTextField jtfPhoneNumber;
	private JTextField jlbDuration;
	private JButton cancel;
	private static BookingService bs = null;
	private static RoomService rs = null;
	static {
		if (bs == null) {
			bs = new BookingService();
		}
		if (rs == null) {
			rs = new RoomService();
		}
	}

	public BookRoomListener(JTextField jtfRoomNum, JTextField jtfPhoneNumber, JTextField jlbDuration, JButton cancel) {
		this.jtfRoomNum = jtfRoomNum;
		this.jtfPhoneNumber = jtfPhoneNumber;
		this.jlbDuration = jlbDuration;
		this.cancel = cancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancel) {
			BookRoomDialog.instance().dispose();
		} else {
			if (jtfPhoneNumber.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "请输入电话号码！");
				return;
			} else {
				bs.addBookingRoom(new Booking(Integer.parseInt(jtfRoomNum.getText()), jtfPhoneNumber.getText(),
						Integer.parseInt(jlbDuration.getText())));
				rs.updateBooked(Integer.parseInt(jtfRoomNum.getText()), 1);

			}
			JOptionPane.showMessageDialog(null,
					"<html>订房成功！房间号：<b><font size=8>" + jtfRoomNum.getText() + "</font></b>");
			BookRoomDialog.instance().dispose();
			MainPanel.instance().refresh();
		}
	}
}
