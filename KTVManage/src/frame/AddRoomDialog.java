package frame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import listener.AddRoomListener;
import service.RoomTypeService;
import entity.RoomType;

public class AddRoomDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7228765492296653353L;
	private static AddRoomDialog addRoomDialog;
	private JComboBox jcbRoomType;
	private JTextField jtfRoomNumber, jtbFloor;
	private static RoomTypeService rts = null;
	static {
		if (rts == null) {
			rts = new RoomTypeService();
		}
	}

	public static AddRoomDialog instance() {
		if (addRoomDialog == null)
			addRoomDialog = new AddRoomDialog();
		return addRoomDialog;
	}

	public AddRoomDialog() {
		super(MainFrame.instance(), "添加房间", true);
		setLayout(null);
		setSize(400, 300);
		setLocationRelativeTo(null);
		addRoomDialog = this;
		JLabel jlbRoomType = new JLabel("房间类型");
		JLabel jlbRoomNumber = new JLabel("房间号码");
		JLabel jlbFloor = new JLabel("房间楼层");

		JButton ensure = new JButton("确定");
		JButton cancel = new JButton("取消");
		jcbRoomType = new JComboBox(rts.getRoomTypeName());
		jtfRoomNumber = new JTextField();
		jtbFloor = new JTextField();

		jlbRoomType.setBounds(60, 30, 85, 30);
		jlbRoomNumber.setBounds(60, 90, 85, 30);
		jlbFloor.setBounds(60, 150, 85, 30);
		jcbRoomType.setBounds(130, 26, 190, 35);
		jtfRoomNumber.setBounds(130, 86, 80, 40);
		jtbFloor.setBounds(130, 146, 190, 40);
		ensure.setBounds(115, 205, 70, 40);
		cancel.setBounds(210, 205, 70, 40);

		AddRoomListener addRoomListener = new AddRoomListener(jcbRoomType, jtfRoomNumber, jtbFloor, cancel);
		jtfRoomNumber.addActionListener(addRoomListener);
		jtbFloor.addActionListener(addRoomListener);
		ensure.addActionListener(addRoomListener);
		cancel.addActionListener(addRoomListener);

		add(jlbRoomType);
		add(jlbRoomNumber);
		add(jlbFloor);
		add(jcbRoomType);
		add(jtfRoomNumber);
		add(jtbFloor);
		add(ensure);
		add(cancel);
	}

	public void open() {
		jcbRoomType.setSelectedIndex(0);
		jtfRoomNumber.setText("0000");
		jtbFloor.setText("1");
		setVisible(true);
	}

}
