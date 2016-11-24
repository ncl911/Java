package frame;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import listener.BookRoomListener;
import dao.ConfigDao;
import entity.RoomType;

public class BookRoomDialog extends JDialog {
	/**
	 * BookingDialog
	 */
	private static final long serialVersionUID = -2103628463004265908L;
	private static BookRoomDialog bookingDialog;
	private JTextField jtfPhoneNumber, jtfDuration,jtfRoomNum;

	public static BookRoomDialog instance() {
		if (bookingDialog == null)
			bookingDialog = new BookRoomDialog();
		return bookingDialog;
	}

	public BookRoomDialog() {
		super(MainFrame.instance(), "订房", true);
		setLayout(null);
		setResizable(false);
		setSize(400, 350);

		bookingDialog = this;

		JLabel jlbSize = new JLabel("房间号码");
		JLabel jlbPhoneNumber = new JLabel("电话号码");
		JLabel jlbDuration = new JLabel("消费时长");
		jtfRoomNum= new JTextField();
		jtfPhoneNumber = new JTextField();
		jtfDuration = new JTextField();
		JButton ensure = new JButton("确定");
		JButton cancel = new JButton("取消");

		jlbSize.setBounds(60, 25, 90, 35);
		jlbPhoneNumber.setBounds(60, 80, 90, 35);
		jlbDuration.setBounds(60, 135, 90, 35);
		jtfRoomNum.setBounds(140, 25, 190, 35);
		jtfPhoneNumber.setBounds(140, 80, 190, 35);
		jtfDuration.setBounds(140, 135, 190, 35);
		ensure.setBounds(100, 250, 90, 40);
		cancel.setBounds(210, 250, 90, 40);

		BookRoomListener bookRoomListener = new BookRoomListener(jtfRoomNum,
				jtfPhoneNumber, jtfDuration,cancel);
		ensure.addActionListener(bookRoomListener);
		cancel.addActionListener(bookRoomListener);
		jtfPhoneNumber.addActionListener(bookRoomListener);

		add(jlbSize);
		add(jlbPhoneNumber);
		add(jlbDuration);
		add(jtfRoomNum);
		add(jtfPhoneNumber);
		add(jtfDuration);
		add(ensure);
		add(cancel);
		setLocationRelativeTo(null);
	}

	public void open(String RoomNum) {
		jtfRoomNum.setText(RoomNum);
		jtfPhoneNumber.setText("");
		jtfDuration.setText("3");
		setVisible(true);
	}
}