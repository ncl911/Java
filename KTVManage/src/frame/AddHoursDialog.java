package frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entity.Room;
import listener.AddHoursListener;
import service.RoomTypeService;

public class AddHoursDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AddHoursDialog addHoursDialog;
	private JTextField jtfRoomNum,jtfHours;
	private static RoomTypeService rts = null;
	static {
		if (rts == null) {
			rts = new RoomTypeService();
		}
	}

	public static AddHoursDialog instance() {
		if (addHoursDialog == null)
			addHoursDialog = new AddHoursDialog();
		return addHoursDialog;
	}

	public AddHoursDialog() {
		super(MainFrame.instance(), "添加时长", true);
		setLayout(null);
		setSize(400, 300);
		setLocationRelativeTo(null);
		addHoursDialog = this;
		JLabel jlbNum = new JLabel("房    号");
		JLabel jlbHours = new JLabel("输入时长");
		JButton ensure = new JButton("确定");
		JButton cancel = new JButton("取消");
		jtfHours = new JTextField(); 
		jtfRoomNum=new JTextField(); 
		
		jlbNum.setBounds(60, 40, 85, 30);
		jlbHours.setBounds(60, 86, 85, 30);
		jtfRoomNum.setBounds(130,40, 80, 40);
		jtfHours.setBounds(130, 86, 80, 40);
		ensure.setBounds(115, 205, 70, 40);
		cancel.setBounds(210, 205, 70, 40);

		AddHoursListener addHoursListener = new AddHoursListener(jtfRoomNum,jtfHours, cancel);
		jtfHours.addActionListener(addHoursListener);
		ensure.addActionListener(addHoursListener);
		cancel.addActionListener(addHoursListener);

		add(jlbNum);
		add(jlbHours);
		add(jtfRoomNum);
		add(jtfHours);
		add(ensure);
		add(cancel);
	}

	public void open(Room room) {
		jtfRoomNum.setText(String.valueOf(room.getId()));
		jtfHours.setText("0");
		setVisible(true);
	}
}
