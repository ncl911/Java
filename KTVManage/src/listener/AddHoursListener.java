package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entity.Room;
import frame.AddHoursDialog;
import frame.MainPanel;
import service.RoomService;

public class AddHoursListener implements ActionListener {

	private JTextField jtfRoomNum, jtfHours;
	private JButton cancel;
	private static RoomService roomService=null;
    static {
    	if(roomService==null){
    		roomService=new RoomService();
    	}
    }
	public AddHoursListener(JTextField jtfRoomNum, JTextField jtfHours, JButton cancel) {
		this.jtfRoomNum = jtfRoomNum;
		this.jtfHours = jtfHours;
		this.cancel = cancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancel) {
			AddHoursDialog.instance().dispose();
			return;
		} else {
			if (jtfHours.getText().equals("0")||jtfHours.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "请输入时长！");
			} else {
				String h = jtfHours.getText();
				Room room=roomService.getRoomById(Integer.parseInt(jtfRoomNum.getText()));
				Date date=null;
				try {
					date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(room.getEndTime());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				date.setHours(date.getHours()+Integer.parseInt(h));
				String newEndTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				roomService.addTime(Integer.parseInt(jtfRoomNum.getText()), newEndTime);
				JOptionPane.showMessageDialog(null, "加时" + h + "小时成功！");
				AddHoursDialog.instance().dispose();
				MainPanel.instance().refresh();
			}
		}
		return;
	}

}
